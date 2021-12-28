package org.wit.recipesapp.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.lifecycle.Observer

import androidx.navigation.fragment.findNavController
import org.wit.recipesapp.databinding.FragmentDetailBinding
import org.wit.recipesapp.ui.auth.LoggedInViewModel
import org.wit.recipesapp.ui.recipeList.RecipeListViewModel


class DetailFragment : Fragment() {


        private lateinit var detailViewModel: DetailViewModel
        private val args by navArgs<DetailFragmentArgs>()
        private var _fragBinding: FragmentDetailBinding? = null
        private val fragBinding get() = _fragBinding!!
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val recipeListViewModel : RecipeListViewModel by activityViewModels()

        override fun onCreateView
                    (inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?
        ): View? {
            _fragBinding = FragmentDetailBinding.inflate(inflater, container, false)
            val root = fragBinding.root

            detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
            detailViewModel.observableRecipe.observe(viewLifecycleOwner, Observer { render() })
            detailViewModel.observableStatus.observe(viewLifecycleOwner, Observer { status ->
            //    status?.let { renderStatus(status) }
            })

            fragBinding.editRecipeButton.setOnClickListener {

                detailViewModel.editRecipe(
                    loggedInViewModel.liveFirebaseUser.value?.uid!!,
                    args.recipeid,
                    fragBinding.recipevm?.observableRecipe!!.value!!
                )
                            recipeListViewModel.load()
                findNavController().navigateUp()
                 }

            fragBinding.deleteRecipeButton.setOnClickListener {
                recipeListViewModel.delete(loggedInViewModel.liveFirebaseUser.value?.uid!!,
                    detailViewModel.observableRecipe.value?.uid!!)
                findNavController().navigateUp()
            }
            return root
        }
//
//    private fun renderStatus(status : Boolean) {
//        when (status) {
//            true -> {
//                view?.let {
//                    findNavController().popBackStack()
//                }
//            }
//            false -> {}
//        }
//    }

        private fun render() {
            fragBinding.recipevm = detailViewModel
        }


    override fun onResume() {
        super.onResume()
        with(detailViewModel) {
            getRecipe(loggedInViewModel.liveFirebaseUser.value?.uid!!, args.recipeid)
        }
    }

        override fun onDestroyView() {
            super.onDestroyView()
            _fragBinding = null
        }
    }
