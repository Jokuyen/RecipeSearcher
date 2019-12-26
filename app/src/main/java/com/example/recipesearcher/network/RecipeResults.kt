package com.example.recipesearcher.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class RecipeResults(@Json(name = "results") val resultsList: List<Recipe>)

@Parcelize
data class Recipe(
    val id: Int,
    @Json(name = "title") val name: String,
    @Json(name = "readyInMinutes") val preparationTime: Int?,
    val servings: Int?,
    @Json(name = "image") val imgUrl: String?
) : Parcelable