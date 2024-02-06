package com.example.color_app_remake.common.extensions

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition


fun ImageView.uploadImageByUrl(url: String) {
    Glide.with(this).asDrawable().load(url).into(this)
}

fun uploadImage(url: String, view: View) {
    Glide.with(view.context).asDrawable().load(url).into(object : CustomTarget<Drawable>() {
        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            view.background = resource
        }

        override fun onLoadCleared(placeholder: Drawable?) {

        }

    })
}