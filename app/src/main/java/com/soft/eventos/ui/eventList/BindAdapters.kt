package com.soft.eventos.ui.eventList

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("image")
fun bindImage(imageView: ImageView, path: String) {

    val options = RequestOptions().apply {
        centerCrop()
    }

    Glide.with(imageView.context)
        .load(path)
        .apply(options)
        .into(imageView)
}