package com.example.recipesearcher.results

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesearcher.databinding.GridViewItemBinding
import com.example.recipesearcher.network.Recipe

class PhotoGridAdapter : ListAdapter<Recipe, PhotoGridAdapter.RecipeViewHolder>(DiffCallback) {
    class RecipeViewHolder(private var binding: GridViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.recipe = recipe
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGridAdapter.RecipeViewHolder {
        return RecipeViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PhotoGridAdapter.RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
    }
}