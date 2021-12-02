package org.wit.recipesapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
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
import org.wit.recipesapp.adapters.RecipeListener
import org.wit.recipesapp.databinding.FragmentRecipeBinding
import org.wit.recipesapp.databinding.FragmentRecipeListBinding
import org.wit.recipesapp.main.MainApp
import org.wit.recipesapp.models.RecipeModel
import org.wit.recipesapp.helpers.createLoader
import org.wit.recipesapp.helpers.hideLoader
import org.wit.recipesapp.helpers.showLoader


class RecipeListFragment : Fragment(), RecipeListener  {
    private var _fragBinding: FragmentRecipeListBinding? = null
    private val fragBinding get() = _fragBinding!!
    lateinit var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var recipeListViewModel: RecipeListViewModel
    private val reportViewModel : RecipeListViewModel by activityViewModels()


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
        activity?.title = getString(R.string.action_recipeList)

        recipeListViewModel =
            ViewModelProvider(this).get(RecipeListViewModel::class.java)
        //val textView: TextView = root.findViewById(R.id.text_gallery)
        reportViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })

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
      //  val action = RecipeListFragmentDirections.actionRecipeListFragmentToRecipeDetailFragment(recipe.id)
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