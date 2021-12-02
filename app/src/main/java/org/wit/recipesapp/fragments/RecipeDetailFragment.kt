package org.wit.recipesapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import org.wit.recipesapp.R


class RecipeDetailFragment : Fragment() {

    companion object {
        fun newInstance() = RecipeDetailFragment()
    }

    private lateinit var viewModel: RecipeDetailViewModel
  //  private val args by navArgs<RecipeDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_recipe_detail, container, false)

   //     Toast.makeText(context, "Donation ID Selected : ${args.recipeid}", Toast.LENGTH_LONG)
     //       .show()

        return view
    }

}

