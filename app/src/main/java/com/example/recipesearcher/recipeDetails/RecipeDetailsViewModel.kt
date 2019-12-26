package com.example.recipesearcher.recipeDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipesearcher.network.Recipe

class RecipeDetailsViewModel(recipe: Recipe, app: Application) : AndroidViewModel(app) {
    private val _selectedRecipe = MutableLiveData<Recipe>()
    val selectedRecipe: LiveData<Recipe>
        get() = _selectedRecipe

    init {
        _selectedRecipe.value = recipe
    }
}
