package org.wit.recipesapp.ui.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.wit.recipesapp.firebase.FirebaseDBManager
import org.wit.recipesapp.firebase.FirebaseImageManager

import org.wit.recipesapp.models.RecipeModel
import timber.log.Timber


class RecipeViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status


    fun addRecipe(firebaseUser: MutableLiveData<FirebaseUser>,
                    recipe: RecipeModel) {
        status.value = try {
            recipe.profilepic = FirebaseImageManager.imageUri.value.toString()
            FirebaseDBManager.create(firebaseUser,recipe)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }


}