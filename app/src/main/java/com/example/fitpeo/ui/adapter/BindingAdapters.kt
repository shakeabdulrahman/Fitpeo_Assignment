package com.example.fitpeo.ui.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.fitpeo.R
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun loadImage(view: View, imageUrl: String?) {
    val image: ImageView = view as ImageView
    Picasso.get().load(imageUrl).placeholder(R.drawable.loading_animation).into(image)
}