package org.wit.recipesapp.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

//interface RecipeStore {
//    fun findAll(): List<RecipeModel>
//    fun create(recipe: RecipeModel)
//    fun update(recipe: RecipeModel)
//    fun delete(recipe: RecipeModel)
//
//
//
//}

interface RecipeStore {
    fun findAll(recipesList:MutableLiveData<List<RecipeModel>>
    )
    fun findAll(userid:String,
                recipesList:MutableLiveData<List<RecipeModel>>
    )
    fun findById(userid:String, recipeid: String, recipe: MutableLiveData<RecipeModel>
    )
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, recipe: RecipeModel)
    fun delete(userid:String, recipeid: String)
    fun update(userid:String, recipeid: String, recipe: RecipeModel)
}