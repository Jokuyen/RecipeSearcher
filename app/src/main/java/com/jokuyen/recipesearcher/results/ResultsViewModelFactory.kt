package com.jokuyen.recipesearcher.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ResultsViewModelFactory(private val userRecipeInput: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultsViewModel::class.java)) {
            return ResultsViewModel(userRecipeInput) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}