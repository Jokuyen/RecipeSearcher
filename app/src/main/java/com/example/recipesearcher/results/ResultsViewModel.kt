package com.example.recipesearcher.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipesearcher.network.ApiServiceObject
import com.example.recipesearcher.network.Recipe
import com.example.recipesearcher.network.RecipeResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                _recipes.value = apiResult.resultsList
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
