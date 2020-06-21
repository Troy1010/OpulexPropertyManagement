package com.example.opulexpropertymanagement.util

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.tmcommonkotlin.logz
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import retrofit2.http.Url


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

@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, urlTask: Task<Url>) {
    urlTask.addOnSuccessListener { url ->
        logz("got url:$url")
        imageView.easyPicasso(url.toString())
    }
}