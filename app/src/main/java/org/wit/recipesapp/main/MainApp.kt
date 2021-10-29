package org.wit.recipesapp.main


import android.app.Application
import org.wit.recipesapp.models.*
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var recipes: RecipeJSONStore
    lateinit var users: UserStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        recipes = RecipeJSONStore(applicationContext)
        users = UserJSONStore(applicationContext)

        i("Placemark started")
    }
}