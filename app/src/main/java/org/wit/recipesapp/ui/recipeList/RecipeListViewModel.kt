package org.wit.recipesapp.ui.recipeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.recipesapp.models.RecipeManager
import org.wit.recipesapp.models.RecipeModel
import timber.log.Timber

class RecipeListViewModel : ViewModel() {

    private val recipeList = MutableLiveData<List<RecipeModel>>()

    val observableRecipesList: LiveData<List<RecipeModel>>
        get() = recipeList

    init {
        load()
        Timber.i("RecipeViewModel  has been created!")
    }

    fun load() {
        recipeList.value = RecipeManager.findAll()
    }
    }