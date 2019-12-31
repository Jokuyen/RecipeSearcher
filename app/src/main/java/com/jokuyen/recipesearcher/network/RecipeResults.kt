package com.jokuyen.recipesearcher.network

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

// Parsing ingredients for selected recipe in details screen
data class IngredientsResult(@Json(name = "ingredients") val ingredientsResult: List<Ingredients>)

data class Ingredients(
    val amount: MeasurementSystem,
    @Json(name = "name") val ingredientName: String
)

data class MeasurementSystem(val metric: Measurement, val us: Measurement)

data class Measurement(
    val unit: String,
    val value: Float
)

// Parsing steps for selected recipe in details screen
data class StepsResults(val name: String, @Json(name = "steps") val stepsResult: List<Steps>)

data class Steps(
    @Json(name = "number") val stepNumber: Int,
    @Json(name = "step") val stepInstruction: String
)