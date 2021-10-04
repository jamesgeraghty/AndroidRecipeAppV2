package org.wit.recipesapp.models

import timber.log.Timber.i


class RecipeMemStore : RecipeStore {

    val recipes = ArrayList<RecipeModel>()

    override fun findAll(): List<RecipeModel> {
        return recipes
    }



    override fun create(placemark: RecipeModel) {
        recipes.add(placemark)
        logAll()
    }

    fun logAll() {
        recipes.forEach{ i("${it}") }
    }
}