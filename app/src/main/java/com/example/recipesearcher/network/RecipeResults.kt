package com.example.recipesearcher.network

import com.squareup.moshi.Json

data class RecipeResults(@Json(name = "results") val resultsList: List<Recipe>)

data class Recipe(
    val id: Int,
    @Json(name = "title") val name: String,
    @Json(name = "readyInMinutes") val preparationTime: Int?,
    val servings: Int?,
    @Json(name = "image") val imgUrl: String?
)