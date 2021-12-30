package org.wit.recipesapp.ui.recipe

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import org.wit.recipesapp.R
//import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.recipesapp.databinding.FragmentRecipeBinding
import org.wit.recipesapp.helpers.showImagePicker
import org.wit.recipesapp.models.RecipeModel
import org.wit.recipesapp.ui.auth.LoggedInViewModel
import org.wit.recipesapp.ui.recipeList.RecipeListViewModel
import timber.log.Timber


class RecipeFragment : Fragment() {

    //lateinit var app: MainApp

    private var _fragBinding: FragmentRecipeBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    lateinit var navController: NavController
    var recipe = RecipeModel()

    private lateinit var recipeViewModel: RecipeViewModel

    private val recipeListViewModel: RecipeListViewModel by activityViewModels()
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()


    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentRecipeBinding.inflate(inflater, container, false)
        val meals = resources.getStringArray(R.array.meals)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, meals)
        val root = fragBinding.root

        activity?.title = getString(R.string.action_recipe)

        recipeViewModel =
            ViewModelProvider(this).get(RecipeViewModel::class.java)
        //val textView: TextView = root.findViewById(R.id.text_home)
        recipeViewModel.observableStatus.observe(viewLifecycleOwner, Observer { status ->
            status?.let { render(status) }
        })

        fragBinding.chooseImage.setOnClickListener {
            Timber.i("Select image")
            showImagePicker(imageIntentLauncher)    // trigger the image picker

        }
        setButtonListener(fragBinding)
        return root;
    }

  //  fragBinding.btnAdd.setOnClickListener() {
  fun setButtonListener(layout: FragmentRecipeBinding) {
      layout.btnAdd.setOnClickListener {
      val meals = resources.getStringArray(R.array.meals)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,meals)
        fragBinding.autoCompleteTextView.setAdapter(arrayAdapter)

        val title = fragBinding.recipeTitle.text.toString()
        val description = fragBinding.description.text.toString()
        recipe.title = fragBinding.recipeTitle.text.toString()
        recipe.description = fragBinding.description.text.toString()
        if (recipe.title.isEmpty()) {
            Snackbar.make(it, R.string.enter_recipe_title, Snackbar.LENGTH_LONG)
                .show()
        } else {

            recipeViewModel.addRecipe(loggedInViewModel.liveFirebaseUser, RecipeModel(title = title,
                description = description,
                email = loggedInViewModel.liveFirebaseUser.value?.email!!))
            Timber.i("add Button Pressed: $recipe.title")

        }
          }
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.recipeError), Toast.LENGTH_LONG).show()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RecipeFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_recipe, menu)
        if (edit) menu.getItem(1).isVisible = true
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()

    }



}