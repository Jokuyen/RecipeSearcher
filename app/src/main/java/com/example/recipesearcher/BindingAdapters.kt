package com.example.recipesearcher

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

const val baseImgUrl: String = "https://spoonacular.com/recipeImages/"

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrlPostfix: String?) {
    imgUrlPostfix?.let {
        var fullImgUrl: String = baseImgUrl + it
        val imgUri = fullImgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}