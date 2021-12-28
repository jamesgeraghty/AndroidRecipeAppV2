//package org.wit.recipesapp.models
//
//import android.net.Uri
//import com.google.gson.*
//import timber.log.Timber.i
//import java.lang.reflect.Type
//
////import com.google.gson.reflect.TypeToken
//
//import java.util.*
//
//
//var lastId = 0L
//
//internal fun getId(): Long {
//    return lastId++
//}
//
//object RecipeManager  : RecipeStore {
//
//    val recipes = ArrayList<RecipeModel>()
//
//    override fun findAll(): List<RecipeModel> {
//        return recipes
//    }
////
//
//    fun findById(id: Long): RecipeModel? {
//        return recipes.find { it.id == id }
//    }
//
//    override fun create(recipe: RecipeModel) {
//        recipe.id = getId()
//        recipes.add(recipe)
//        logAll()
//    }
//
//    override fun update(recipe: RecipeModel) {
//        var foundRecipe: RecipeModel? = recipes.find { p -> p.id == recipe.id }
//        if (foundRecipe != null) {
//            foundRecipe.title = recipe.title
//            foundRecipe.description = recipe.description
//            foundRecipe.image = recipe.image
//            foundRecipe.lat = recipe.lat
//            foundRecipe.lng = recipe.lng
//            foundRecipe.zoom = recipe.zoom
//            logAll()
//        }
//    }
//
//    override fun delete(recipe: RecipeModel) {
//        val recipeList = findAll() as java.util.ArrayList<RecipeModel>
//        var foundRecipe: RecipeModel? = recipes.find { p -> p.id == recipe.id }
//        if (foundRecipe != null) {
//            recipes.remove(recipe)
//
//            logAll()
//        }
//    }
//
//    private fun logAll() {
//        recipes.forEach { i("$it") }
//    }
//}
//
//class UriParser : JsonDeserializer<Uri>, JsonSerializer<Uri> {
//    override fun deserialize(
//        json: JsonElement?,
//        typeOfT: Type?,
//        context: JsonDeserializationContext?
//    ): Uri {
//        return Uri.parse(json?.asString)
//    }
//
//    override fun serialize(
//        src: Uri?,
//        typeOfSrc: Type?,
//        context: JsonSerializationContext?
//    ): JsonElement {
//        return JsonPrimitive(src.toString())
//    }
//
//
//}