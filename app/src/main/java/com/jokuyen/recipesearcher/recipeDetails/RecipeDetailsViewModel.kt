package com.jokuyen.recipesearcher.recipeDetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jokuyen.recipesearcher.network.ApiServiceObject
import com.jokuyen.recipesearcher.network.Ingredients
import com.jokuyen.recipesearcher.network.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class apiStatus { LOADING, ERROR, DONE }

class RecipeDetailsViewModel(recipe: Recipe, app: Application) : AndroidViewModel(app) {

    private val _status = MutableLiveData<apiStatus>()
    val status: LiveData<apiStatus>
        get() = _status

    private val _selectedRecipe = MutableLiveData<Recipe>()
    val selectedRecipe: LiveData<Recipe>
        get() = _selectedRecipe

    private val _ingredientsList = MutableLiveData<List<Ingredients>>()
    val ingredientsList: LiveData<List<Ingredients>>
        get() = _ingredientsList

    // Coroutine setup
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        _selectedRecipe.value = recipe
        getDetails()
    }

    private fun getDetails() {
        coroutineScope.launch {
            var getIngredientsDeferred = ApiServiceObject.retrofitService.getIngredients(selectedRecipe.value!!.id)

            try {
                _status.value = apiStatus.LOADING
                var apiResult = getIngredientsDeferred.await()
                _status.value = apiStatus.DONE
                _ingredientsList.value = apiResult.ingredientsResult
            } catch (t: Throwable) {
                _status.value = apiStatus.ERROR
                //_selectedRecipe.value = null
                Log.i("RecipeDetailsViewModel", "ERROR: $t")
            }
        }
    }
}
