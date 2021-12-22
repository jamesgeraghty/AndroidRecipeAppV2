package org.wit.recipesapp.ui.recipe

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.recipesapp.R
//import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.recipesapp.databinding.FragmentRecipeBinding
import org.wit.recipesapp.helpers.showImagePicker
import org.wit.recipesapp.main.MainApp
import org.wit.recipesapp.models.RecipeModel
import org.wit.recipesapp.models.UserModel
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RecipeFragment : Fragment() {

    lateinit var app: MainApp

    private var _fragBinding: FragmentRecipeBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    lateinit var navController: NavController
    var recipe = RecipeModel()
    var user = UserModel()
    private lateinit var recipeViewModel: RecipeViewModel


    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = activity?.application as MainApp

        setHasOptionsMenu(true)
        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment)
        registerImagePickerCallback()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentRecipeBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_recipe)

        recipeViewModel =
            ViewModelProvider(this).get(RecipeViewModel::class.java)
        //val textView: TextView = root.findViewById(R.id.text_home)
        recipeViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })

        fragBinding.btnAdd.setOnClickListener() {
            recipe.title = fragBinding.recipeTitle.text.toString()
            recipe.description = fragBinding.description.text.toString()
            if (recipe.title.isEmpty()) {
                Snackbar.make(it, R.string.enter_recipe_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {

                    recipeViewModel.addRecipe(recipe.copy())
                    Timber.i("add Button Pressed: $recipe.title")

                navController.navigate(R.id.recipeListFragment)
            }
        }
        fragBinding.chooseImage.setOnClickListener {
            Timber.i("Select image")
            showImagePicker(imageIntentLauncher)    // trigger the image picker

        }
        return root;
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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            recipe.image = result.data!!.data!!
                            Picasso.get()
                                .load(recipe.image)
                                .into(fragBinding.recipeImage)
                            fragBinding.chooseImage.setText(R.string.change_recipe_image)
                        }
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }

}