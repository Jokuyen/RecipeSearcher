package com.example.recipesearcher.network

import com.example.recipesearcher.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://api.spoonacular.com/"
private val API_KEY = BuildConfig.API_KEY

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    @GET("recipes/search")
    fun getRecipe(
        @Query("query") queryInput: String,
        @Query("apiKey") apiKeyInput: String = API_KEY
    ): Call<RecipeResults>

}

object ApiServiceObject {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}