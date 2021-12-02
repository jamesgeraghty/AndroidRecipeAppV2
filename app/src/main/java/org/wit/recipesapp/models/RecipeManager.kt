package org.wit.recipesapp.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object RecipeManager  : RecipeStore {

    val recipes = ArrayList<RecipeModel>()

    override fun findAll(): List<RecipeModel> {
        return recipes
    }

    override fun create(recipe: RecipeModel) {
        recipe.id = getId()
        recipes.add(recipe)
        logAll()
    }

    override fun update(recipe: RecipeModel) {
        var foundRecipe: RecipeModel? = recipes.find { p -> p.id == recipe.id }
        if (foundRecipe != null) {
            foundRecipe.title = recipe.title
            foundRecipe.description = recipe.description
            foundRecipe.image = recipe.image
            foundRecipe.lat = recipe.lat
            foundRecipe.lng = recipe.lng
            foundRecipe.zoom = recipe.zoom
            logAll()
        }
    }

    override fun delete(recipe: RecipeModel) {
        var foundRecipe: RecipeModel? = recipes.find { p -> p.id == recipe.id }
        if (foundRecipe != null) {
            recipes.remove(recipe)

            logAll()
        }
    }

    private fun logAll() {
        recipes.forEach { i("$it") }
    }
}