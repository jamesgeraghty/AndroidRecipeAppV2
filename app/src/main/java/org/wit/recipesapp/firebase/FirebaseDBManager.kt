package org.wit.recipesapp.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import org.wit.recipesapp.models.RecipeModel
import org.wit.recipesapp.models.RecipeStore
import timber.log.Timber

object FirebaseDBManager : RecipeStore {


    var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    //functionused to view all the recipes available
    override fun findAll(recipesList: MutableLiveData<List<RecipeModel>>) {
        database.child("recipes")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Recipe error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<RecipeModel>()
                    val children = snapshot.children
                    children.forEach {
                        val recipe = it.getValue(RecipeModel::class.java)
                        localList.add(recipe!!)
                    }
                    database.child("recipes")
                        .removeEventListener(this)

                    recipesList.value = localList
                }
            })
    }

    override fun findAll(userid: String,recipesList: MutableLiveData<List<RecipeModel>>) {
        database.child("user-recipes")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Recipe error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<RecipeModel>()
                    val children = snapshot.children
                    children.forEach {
                        val recipe = it.getValue(RecipeModel::class.java)
                        localList.add(recipe!!)
                    }
                    database.child("user-recipes")
                        .removeEventListener(this)

// updated with the lcallist retreieved from the database
                    recipesList.value = localList
                }
            })
    }


    override fun findById(userid: String, recipeid: String, recipe: MutableLiveData<RecipeModel>) {

        database.child("user-recipes").child(userid)
            .child(recipeid.toString()).get().addOnSuccessListener {
                recipe.value = it.getValue(RecipeModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }
    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, recipe: RecipeModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("recipes").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        recipe.uid = key
        val recipeValues = recipe.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/recipes/$key"] = recipeValues
        childAdd["/user-recipes/$uid/$key"] = recipeValues

        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, recipeid: String) {
        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/recipes/$recipeid"] = null
        childDelete["/user-recipes/$userid/$recipeid"] = null

        database.updateChildren(childDelete)
    }

    override fun update(userid: String, recipeid: String, recipe: RecipeModel) {

        val recipeValues = recipe.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["recipes/$recipeid"] = recipeValues
        childUpdate["user-recipes/$userid/$recipeid"] = recipeValues

        database.updateChildren(childUpdate)
    }
    fun updateImageRef(userid: String,imageUri: String) {

        val userRecipes = database.child("user-recipes").child(userid)
        val allRecipes = database.child("recipes")

        userRecipes.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        //Update Users imageUri
                        it.ref.child("profilepic").setValue(imageUri)
                        //Update all donations that match 'it'
                        val recipe = it.getValue(RecipeModel::class.java)
                        allRecipes.child(recipe!!.uid!!)
                            .child("profilepic").setValue(imageUri)
                    }
                }
            })
    }
}