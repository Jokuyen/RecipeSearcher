package com.jokuyen.recipesearcher.recipeDetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jokuyen.recipesearcher.network.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.util.*

class RecipeDetailsViewModel(recipe: Recipe, app: Application) : AndroidViewModel(app) {

    private val _selectedRecipe = MutableLiveData<Recipe>()
    val selectedRecipe: LiveData<Recipe>
        get() = _selectedRecipe

    // Variables for API results
    private val _ingredientsList = MutableLiveData<List<Ingredients>>()
    val ingredientsList: LiveData<List<Ingredients>>
        get() = _ingredientsList

    private val _stepsList = MutableLiveData<List<StepsResults>>()
    val stepsList: LiveData<List<StepsResults>>
        get() = _stepsList

    // Variables for strings used in XML data binding
    private val _ingredientsListString = MutableLiveData<String>()
    val ingredientsListString: LiveData<String>
        get() = _ingredientsListString

    private val _stepsListString = MutableLiveData<String>()
    val stepsListString: LiveData<String>
        get() = _stepsListString

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
                var ingredientsApiResult = getIngredientsDeferred.await()
                _ingredientsList.value = ingredientsApiResult.ingredientsResult
                createIngredientsString()
            } catch (t: Throwable) {
                Log.i("RecipeDetailsViewModel", "ERROR: $t")
            }
        }

        coroutineScope.launch {
            var getStepsDeferred = ApiServiceObject.retrofitService.getSteps(selectedRecipe.value!!.id)

            try {
                var stepsApiResult = getStepsDeferred.await()
                _stepsList.value = stepsApiResult
                createStepsString()
            } catch (t: Throwable) {
                Log.i("RecipeDetailsViewModel", "ERROR: $t")
            }
        }
    }

    private fun createIngredientsString() {
        var result: String = ""

        ingredientsList.value?.let {
            result += it.joinToString(separator = "\n\n") {
                "${it.ingredientName.capitalize()}\n" +
                        "Metric: ${it.amount.metric.value} ${it.amount.metric.unit}\n" +
                        "Imperial: ${it.amount.us.value} ${it.amount.us.unit}"
            }
        }
        _ingredientsListString.value = result
    }

    private fun createStepsString() {
        var result: String = ""

        stepsList.value?.let {
            var firstStepName: Boolean = true // To avoid appending "\n\n" to the first step's name if it exist

            for (element in it) {
                if (firstStepName) {
                    if (element.name.isNotEmpty()) {
                        result += "${element.name}:\n\n"
                    }
                    firstStepName = false
                }
                else if (element.name.isNotEmpty()) {
                    result += "\n\n${element.name}:\n\n"
                }

                result += element.stepsResult.joinToString(separator = "\n\n") {
                        "${it.stepNumber}) ${it.stepInstruction}"
                }
            }
        }
        _stepsListString.value = result
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
