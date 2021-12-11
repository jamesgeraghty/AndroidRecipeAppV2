package org.wit.recipesapp.ui.recipeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.recipesapp.models.RecipeManager
import org.wit.recipesapp.models.RecipeModel

class RecipeListViewModel : ViewModel() {

    private val recipeList = MutableLiveData<List<RecipeModel>>()

    val observableRecipesList: LiveData<List<RecipeModel>>
        get() = recipeList

    init {
        load()
    }

    fun load() {
        recipeList.value = RecipeManager.findAll()
    }
    }