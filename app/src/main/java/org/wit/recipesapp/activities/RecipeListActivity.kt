package org.wit.recipesapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.recipesapp.R
import org.wit.recipesapp.adapters.RecipeAdapter
import org.wit.recipesapp.adapters.RecipeListener

import org.wit.recipesapp.databinding.ActivityRecipeListBinding
import org.wit.recipesapp.databinding.CardRecipeBinding
import org.wit.recipesapp.main.MainApp
import org.wit.recipesapp.models.RecipeModel

class RecipeListActivity : AppCompatActivity(), RecipeListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityRecipeListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
       // binding.recyclerView.adapter = RecipeAdapter(app.recipes)
        binding.recyclerView.adapter = RecipeAdapter(app.recipes.findAll(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, RecipeActivity::class.java)
                startActivityForResult(launcherIntent, 0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRecipeClick(recipe: RecipeModel) {
        val launcherIntent = Intent(this, RecipeActivity::class.java)
        startActivityForResult(launcherIntent,0)
    }

}
