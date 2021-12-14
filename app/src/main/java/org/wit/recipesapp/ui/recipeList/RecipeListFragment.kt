package org.wit.recipesapp.ui.recipeList

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.wit.recipesapp.R
import org.wit.recipesapp.adapters.RecipeAdapter
import org.wit.recipesapp.adapters.RecipeClickListener

import org.wit.recipesapp.databinding.FragmentRecipeListBinding
import org.wit.recipesapp.main.MainApp
import org.wit.recipesapp.models.RecipeModel
import org.wit.recipesapp.ui.auth.LoggedInViewModel


class RecipeListFragment : Fragment(), RecipeClickListener {

    lateinit var app: MainApp
    private var _fragBinding: FragmentRecipeListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var recipeListViewModel: RecipeListViewModel

    private val reportViewModel: RecipeListViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

    //private val recipeListViewModel : RecipeListViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentRecipeListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        //activity?.title = getString(R.string.action_recipeList)

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        recipeListViewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)
        recipeListViewModel.observableRecipesList.observe(viewLifecycleOwner, Observer {
            //   recipes ->
          // recipes?.let { render(recipes) }
        })

      fragBinding.recyclerView.adapter = RecipeAdapter(app.recipes.findAll(), this)
        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = RecipeListFragmentDirections.actionRecipeListFragmentToDetailFragment()
            findNavController().navigate(action)
        }
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

    private fun render(recipeList: List<RecipeModel>) {
        fragBinding.recyclerView.adapter = RecipeAdapter(recipeList, this)
        if (recipeList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.recipesNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.recipesNotFound.visibility = View.GONE
        }
    }

    override fun onRecipeClick(recipe: RecipeModel) {
        val action = RecipeListFragmentDirections.actionRecipeListFragmentToDetailFragment()
        findNavController().navigate(action)
    }


    fun showRecipes (recipe: List<RecipeModel>) {
        fragBinding.recyclerView.adapter = RecipeAdapter(recipe, this)
        fragBinding.recyclerView.adapter?.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RecipeListFragment().apply {
                arguments = Bundle().apply { }
            }
    }
    override fun onResume() {
        super.onResume()
        recipeListViewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    private fun loadRecipes() {
        showRecipes(app.recipes.findAll())
    }




}