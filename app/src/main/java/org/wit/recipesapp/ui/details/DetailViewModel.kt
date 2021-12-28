package org.wit.recipesapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.recipesapp.firebase.FirebaseDBManager

import org.wit.recipesapp.models.RecipeModel
import timber.log.Timber
import java.lang.Exception

class DetailViewModel : ViewModel() {
    private val recipe = MutableLiveData<RecipeModel>()
    var observableRecipe: MutableLiveData<RecipeModel>
        get() = recipe
        set(value) {recipe.value = value.value}

    private val status = MutableLiveData<Boolean>()
    val observableStatus: LiveData<Boolean>
        get() = status

    fun getRecipe(userid:String, id: String) {
        try {
            FirebaseDBManager.findById(userid, id, recipe)
            Timber.i("Detail getRecipe() Success : ${
                recipe.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getRecipe() Error : $e.message")
        }
    }

    fun editRecipe(userid: String, id: String, recipe: RecipeModel) {
        try {

            FirebaseDBManager.update(userid, id, recipe)
            Timber.i("Detail editRecipe() Success : $recipe")
        }
        catch (e: Exception) {
            Timber.i("Detail editRecipe() Error : $e.message")
        }
    }

}