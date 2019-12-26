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

class ResultsViewModel(userRecipeInput: String) : ViewModel() {

    private val _userRecipeInput: String = userRecipeInput

    private val _apiStatus = MutableLiveData<String>()
    val apiStatus: LiveData<String>
        get() = _apiStatus

    // LiveData Recipe property with internal Mutable and external LiveData
    private val _recipe = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe>
        get() = _recipe

    // Coroutine setup
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        _apiStatus.value = "Loading..."
        getRecipeResults()
    }

    private fun getRecipeResults() {
        coroutineScope.launch {
            var getRecipesDeferred = ApiServiceObject.retrofitService.getRecipe(_userRecipeInput)
            try {
                var apiResult = getRecipesDeferred.await()
                if (apiResult.resultsList.size > 0) {
                    _recipe.value = apiResult.resultsList[0]
                }
            } catch (t: Throwable) {
                _apiStatus.value = "Failure: " + t.message
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
