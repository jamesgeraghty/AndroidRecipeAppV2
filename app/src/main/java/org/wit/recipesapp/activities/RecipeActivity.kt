package org.wit.recipesapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.recipesapp.R
import org.wit.recipesapp.databinding.ActivityRecipeBinding

import org.wit.recipesapp.main.MainApp
import org.wit.recipesapp.models.RecipeModel

import timber.log.Timber
import timber.log.Timber.i

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding
    var recipe = RecipeModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp

        i("Recipes Activity started...")

        if (intent.hasExtra("recipe_edit")) {
            edit = true
            recipe = intent.extras?.getParcelable("recipe_edit")!!
            binding.recipeTitle.setText(recipe.title)
            binding.description.setText(recipe.description)
            binding.btnAdd.setText(R.string.save_recipe)
        }

        binding.btnAdd.setOnClickListener() {
            recipe.title = binding.recipeTitle.text.toString()
            recipe.description = binding.description.text.toString()
            if (recipe.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_recipe_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.recipes.update(recipe.copy())
                } else {
                    app.recipes.create(recipe.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_recipe, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}