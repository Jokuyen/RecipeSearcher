package com.example.recipesearcher.recipeDetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipesearcher.network.Recipe

class RecipeDetailsViewModelFactory(private val recipe: Recipe, private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeDetailsViewModel::class.java)) {
            return RecipeDetailsViewModel(recipe, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}