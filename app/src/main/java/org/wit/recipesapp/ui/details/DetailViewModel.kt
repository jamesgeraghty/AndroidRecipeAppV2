package org.wit.recipesapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.recipesapp.models.RecipeManager
import org.wit.recipesapp.models.RecipeModel

class DetailViewModel : ViewModel() {
    private val recipe = MutableLiveData<RecipeModel>()
    var observableRecipe: MutableLiveData<RecipeModel>
        get() = recipe
        set(value) {recipe.value = value.value}

    private val status = MutableLiveData<Boolean>()
    val observableStatus: LiveData<Boolean>
        get() = status


    fun getRecipe(id: Long) {
        recipe.value = RecipeManager.findById(id)
    }

    fun editRecipe(recipeModel: RecipeModel){
        status.value = try {
            RecipeManager.update(recipeModel)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
    fun deleteRecipe(recipeModel: RecipeModel){
        status.value = try {
            RecipeManager.delete(recipeModel)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}