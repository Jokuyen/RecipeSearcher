package com.example.recipesearcher.network

import com.example.recipesearcher.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
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
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    @GET("recipes/search")
    fun getRecipe(
        @Query("query") queryInput: String,
        @Query("apiKey") apiKeyInput: String = API_KEY
    ): Deferred<RecipeResults>

}

object ApiServiceObject {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}