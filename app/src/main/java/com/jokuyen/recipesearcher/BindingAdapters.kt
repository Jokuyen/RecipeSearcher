package com.jokuyen.recipesearcher

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jokuyen.recipesearcher.network.Recipe
import com.jokuyen.recipesearcher.results.RecyclerViewAdapter
import com.jokuyen.recipesearcher.results.apiStatus

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
    val adapter = recyclerView.adapter as RecyclerViewAdapter
    adapter.submitList(data)
}

@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: apiStatus?) {
    when (status) {
        apiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        apiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource((R.drawable.ic_connection_error))
        }
        apiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}