package com.example.recipesearcher

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipesearcher.network.Recipe
import com.example.recipesearcher.results.PhotoGridAdapter

const val baseImgUrl: String = "https://spoonacular.com/recipeImages/"

// Use Glide library to load image from URL
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

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Recipe>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}