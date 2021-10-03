package org.wit.recipesapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.wit.recipesapp.R
import org.wit.recipesapp.main.MainApp


class RecipeListActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)
        app = application as MainApp
    }
}