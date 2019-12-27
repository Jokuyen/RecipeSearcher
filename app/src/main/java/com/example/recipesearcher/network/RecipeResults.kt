package com.example.recipesearcher.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

// Parsing results after search prompt
data class RecipeResults(val results: List<Recipe>)

@Parcelize
data class Recipe(
    val id: Int,
    @Json(name = "title") val name: String,
    @Json(name = "readyInMinutes") val preparationTime: Int?,
    val servings: Int?,
    @Json(name = "image") val imgUrl: String?
) : Parcelable

// Parsing steps for selected recipe in details screen
data class StepsResult(val steps: List<StepsList>)

data class StepsList(
    val number: Int,
    val step: String
)

// Parsing ingredients for selected recipe in details screen
data class IngredientsResults(@Json(name = "ingredients") val ingredientsResult: List<Ingredients>)

data class Ingredients(
    val amount: MeasurementSystem,
    val name: String
)

data class MeasurementSystem(val metric: Measurement)

data class Measurement(
    val unit: String,
    val value: Float
)