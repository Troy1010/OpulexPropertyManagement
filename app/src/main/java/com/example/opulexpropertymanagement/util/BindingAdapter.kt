package com.example.opulexpropertymanagement.util

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout




@BindingAdapter("app:errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
    view.error = errorMessage
}

@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, uri: Uri) {
    imageView.setImageURI(uri)
}