package org.wit.recipesapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.recipesapp.R
import org.wit.recipesapp.adapters.RecipeAdapter
import org.wit.recipesapp.adapters.RecipeListener
import org.wit.recipesapp.databinding.FragmentRecipeListBinding
import org.wit.recipesapp.main.MainApp
import org.wit.recipesapp.models.RecipeModel


class RecipeListFragment : Fragment(), RecipeListener  {
    private var _fragBinding: FragmentRecipeListBinding? = null
    private val fragBinding get() = _fragBinding!!
    lateinit var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)

        registerRefreshCallback()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentRecipeListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.menu_addRecipe)
        fragBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        fragBinding.recyclerView.adapter = RecipeAdapter(app.recipes.findAll(), this)
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_recipe_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }
    override fun onRecipeClick(recipe: RecipeModel) {
        findNavController().navigate(R.id.recipeFragment)
    }


    fun showRecipes (recipes: List<RecipeModel>) {
        fragBinding.recyclerView.adapter = RecipeAdapter(recipes, this)
        fragBinding.recyclerView.adapter?.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RecipeListFragment().apply {
                arguments = Bundle().apply { }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }



    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadRecipes() }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
    private fun loadRecipes() {
        showRecipes(app.recipes.findAll())
    }




}