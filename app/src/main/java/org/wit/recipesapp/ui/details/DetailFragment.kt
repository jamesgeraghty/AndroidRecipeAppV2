package org.wit.recipesapp.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.lifecycle.Observer

import androidx.navigation.fragment.findNavController
import org.wit.recipesapp.databinding.FragmentDetailBinding
import timber.log.Timber


class DetailFragment : Fragment() {


        private lateinit var detailViewModel: DetailViewModel
        private val args by navArgs<DetailFragmentArgs>()
        private var _fragBinding: FragmentDetailBinding? = null
        private val fragBinding get() = _fragBinding!!

        override fun onCreateView
                    (inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?
        ): View? {
            _fragBinding = FragmentDetailBinding.inflate(inflater, container, false)
            val view = fragBinding.root

            detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
            detailViewModel.observableRecipe.observe(viewLifecycleOwner, Observer { render() })
            detailViewModel.observableStatus.observe(viewLifecycleOwner, Observer { status ->
                status?.let { renderStatus(status) }
            })

            fragBinding.editRecipeButton.setOnClickListener {

                detailViewModel.editRecipe(fragBinding.recipevm?.observableRecipe!!.value!!)
            }
            fragBinding.deleteRecipeButton.setOnClickListener {
                detailViewModel.deleteRecipe(fragBinding.recipevm?.observableRecipe!!.value!!)
            }
            return view
        }

    private fun renderStatus(status : Boolean) {
        when (status) {
            true -> {
                view?.let {
                    findNavController().popBackStack()
                }
            }
            false -> {}
        }
    }

        private fun render() {

            fragBinding.recipevm = detailViewModel
        }

    override fun onResume() {
        super.onResume()
        detailViewModel.getRecipe(args.recipeid)
    }

        override fun onDestroyView() {
            super.onDestroyView()
            _fragBinding = null
        }
    }
