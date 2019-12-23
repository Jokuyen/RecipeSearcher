package com.example.recipesearcher.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipesearcher.network.ApiServiceObject
import com.example.recipesearcher.network.RecipeResults
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultsViewModel(userRecipeInput: String) : ViewModel() {

    private val _userRecipeInput: String = userRecipeInput

    private val _apiResponse = MutableLiveData<String>()

    val apiResponse: LiveData<String>
        get() = _apiResponse

    init {
        _apiResponse.value = "Loading..."
        getRecipeResults()
    }

    private fun getRecipeResults() {
        ApiServiceObject.retrofitService.getRecipe(_userRecipeInput).enqueue(object : Callback<RecipeResults> {
            override fun onFailure(call: Call<RecipeResults>, t: Throwable) {
                _apiResponse.value = "Failure: " + t.message
            }

            override fun onResponse(call: Call<RecipeResults>, response: Response<RecipeResults>) {
                _apiResponse.value = "Success: " + response.body()
            }
        })
    }
}
