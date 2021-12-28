
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

class RecipeAdapter (private var recipes: ArrayList<RecipeModel>,
                     private val listener: RecipeClickListener, private val readOnly: Boolean)

    :RecyclerView.Adapter<RecipeAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardRecipeBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding,readOnly)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val recipe = recipes[holder.adapterPosition]
        holder.bind(recipe, listener)
    }
    fun removeAt(position: Int) {
        recipes.removeAt(position)
        notifyItemRemoved(position)
    }
    override fun getItemCount(): Int = recipes.size

    class MainHolder( val binding: CardRecipeBinding,private val readOnly : Boolean) :
        RecyclerView.ViewHolder(binding.root) {

        val readOnlyRow = readOnly


        fun bind(recipe: RecipeModel, listener: RecipeClickListener) {
            binding.root.tag = recipe
            binding.recipe = recipe
           // Picasso.get().load(recipe.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onRecipeClick(recipe) }
            binding.executePendingBindings()
        }
    }


}