package org.wit.recipesapp.models

interface RecipeStore {
    fun findAll(): List<RecipeModel>
    fun create(placemark: RecipeModel)
}