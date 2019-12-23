package com.example.recipesearcher.network

import com.squareup.moshi.Json

const val baseImgUrl: String = "https://spoonacular.com/recipeImages/"

data class RecipeResults(val results: List<Recipe>)

data class Recipe(
    val id: Int,
    @Json(name = "title") val name: String,
    @Json(name = "readyInMinutes") val preparationTime: Int?,
    val servings: Int?,
    @Json(name = "image") val imgUrl: String?
)