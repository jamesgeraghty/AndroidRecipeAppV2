
package org.wit.recipesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

import org.wit.recipesapp.databinding.CardRecipeBinding
import org.wit.recipesapp.models.RecipeModel

interface RecipeClickListener {
    fun onRecipeClick(recipe: RecipeModel)
}

class RecipeAdapter (private var recipes: List<RecipeModel>,
                     private val listener: RecipeClickListener)

    :RecyclerView.Adapter<RecipeAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardRecipeBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val recipe = recipes[holder.adapterPosition]
        holder.bind(recipe, listener)
    }

    override fun getItemCount(): Int = recipes.size

    class MainHolder( val binding: CardRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: RecipeModel, listener: RecipeClickListener) {
            binding.root.tag = recipe
            binding.recipe = recipe
            Picasso.get().load(recipe.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onRecipeClick(recipe) }
            binding.executePendingBindings()
        }
    }
}