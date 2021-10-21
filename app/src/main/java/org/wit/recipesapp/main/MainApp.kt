package org.wit.recipesapp.main


import android.app.Application
import org.wit.recipesapp.models.RecipeJSONStore
import org.wit.recipesapp.models.RecipeMemStore
import org.wit.recipesapp.models.RecipeModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var recipes: RecipeJSONStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        recipes = RecipeJSONStore(applicationContext)
        i("Placemark started")
    }
}