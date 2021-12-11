package org.wit.recipesapp.ui.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.recipesapp.models.RecipeManager
import org.wit.recipesapp.models.RecipeModel

class RecipeViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addRecipe(recipe: RecipeModel) {
        status.value = try {
            RecipeManager.create(recipe)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}