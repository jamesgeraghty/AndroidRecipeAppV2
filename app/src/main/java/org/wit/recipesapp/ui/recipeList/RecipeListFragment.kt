package org.wit.recipesapp.ui.recipeList

import android.app.AlertDialog
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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.wit.recipesapp.R
import org.wit.recipesapp.adapters.RecipeAdapter
import org.wit.recipesapp.adapters.RecipeClickListener

import org.wit.recipesapp.databinding.FragmentRecipeListBinding
import org.wit.recipesapp.helpers.createLoader
import org.wit.recipesapp.helpers.showLoader
import org.wit.recipesapp.main.MainApp
import org.wit.recipesapp.models.RecipeModel
import org.wit.recipesapp.models.UserModel
import org.wit.recipesapp.ui.auth.LoggedInViewModel
import org.wit.recipesapp.utils.SwipeToDeleteCallback
import org.wit.recipesapp.utils.hideLoader


class RecipeListFragment : Fragment(), RecipeClickListener {

    lateinit var app: MainApp
    private var _fragBinding: FragmentRecipeListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var recipeListViewModel: RecipeListViewModel
    lateinit var loader : AlertDialog
   // private val recipeListViewModel: RecipeListViewModel by activityViewModels()
  private val loggedInViewModel : LoggedInViewModel by activityViewModels()
//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // app = activity?.application as MainApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loader = createLoader(requireActivity())
        _fragBinding = FragmentRecipeListBinding.inflate(inflater, container, false)
        val view = fragBinding.root
     //   activity?.title = getString(R.string.action_recipeList)

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        recipeListViewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)
        recipeListViewModel.observableRecipesList.observe(viewLifecycleOwner, Observer {
               recipes ->
           recipes?.let { render(recipes as ArrayList<RecipeModel>)}
            hideLoader(loader)
            checkSwipeRefresh()
        })
        setSwipeRefresh()
        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = fragBinding.recyclerView.adapter as RecipeAdapter
                adapter.removeAt(viewHolder.adapterPosition)

            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = RecipeListFragmentDirections.actionRecipeListFragmentToRecipeFragment(this.toString())
            findNavController().navigate(action)
        }
        return view
    }
    private fun render(recipeList: ArrayList<RecipeModel>) {
        fragBinding.recyclerView.adapter = RecipeAdapter(recipeList, this)
        if (recipeList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.recipesNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.recipesNotFound.visibility = View.GONE
        }
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
        val action = RecipeListFragmentDirections.actionRecipeListFragmentToDetailFragment(recipe.id)
        findNavController().navigate(action)
    }


    fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader,"Downloading Recipes")
            //Retrieve Donation List again here

        }
    }

    fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
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

//    private fun loadRecipes() {
//        showRecipes(app.recipes.findAll())
//    }




}