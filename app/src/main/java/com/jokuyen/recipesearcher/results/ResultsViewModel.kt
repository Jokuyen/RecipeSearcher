package com.jokuyen.recipesearcher.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jokuyen.recipesearcher.network.ApiServiceObject
import com.jokuyen.recipesearcher.network.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class apiStatus { LOADING, ERROR, DONE }

class ResultsViewModel(userRecipeInput: String) : ViewModel() {

    private val _userRecipeInput: String = userRecipeInput

    private val _status = MutableLiveData<apiStatus>()
    val status: LiveData<apiStatus>
        get() = _status

    // LiveData Recipe property with internal Mutable and external LiveData
    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>>
        get() = _recipes

    private val _navigateToSelectedRecipe = MutableLiveData<Recipe>()
    val navigateToSelectedRecipe
        get() = _navigateToSelectedRecipe

    // Coroutine setup
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getRecipeResults()
    }

    private fun getRecipeResults() {
        coroutineScope.launch {
            var getRecipesDeferred = ApiServiceObject.retrofitService.getRecipe(_userRecipeInput)

            try {
                _status.value = apiStatus.LOADING
                var apiResult = getRecipesDeferred.await()
                _status.value = apiStatus.DONE
                _recipes.value = apiResult.results
            } catch (t: Throwable) {
                _status.value = apiStatus.ERROR
                _recipes.value = ArrayList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    // Navigation to recipe's details functions
    fun displayRecipeDetails(recipe: Recipe) {
        _navigateToSelectedRecipe.value = recipe
    }

    fun displayRecipeDetailsComplete() {
        _navigateToSelectedRecipe. value = null
    }
}
