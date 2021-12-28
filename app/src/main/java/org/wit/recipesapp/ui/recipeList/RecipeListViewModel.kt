package org.wit.recipesapp.ui.recipeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.wit.recipesapp.firebase.FirebaseDBManager
//import org.wit.recipesapp.models.RecipeManager
import org.wit.recipesapp.models.RecipeModel
import timber.log.Timber
import java.lang.Exception

class RecipeListViewModel : ViewModel() {

    private val recipesList = MutableLiveData<List<RecipeModel>>()

    val observableRecipesList: LiveData<List<RecipeModel>>
        get() = recipesList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    var readOnly = MutableLiveData(false)

    init {
        load()
        Timber.i("RecipeViewModel  has been created!")
    }


    fun load() {
        try {
            readOnly.value = false
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!,
                recipesList)
            Timber.i("Report Load Success : ${recipesList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }
    }

    fun loadAll() {
        try {
            readOnly.value = true
            FirebaseDBManager.findAll(recipesList)
            Timber.i("Report LoadAll Success : ${recipesList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report LoadAll Error : $e.message")
        }
    }

    fun delete(userid: String, id: String) {
        try {
            FirebaseDBManager.delete(userid,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }

    }