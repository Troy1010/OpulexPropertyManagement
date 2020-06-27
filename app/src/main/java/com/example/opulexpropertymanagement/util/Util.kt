package com.example.opulexpropertymanagement.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.app.App
import com.example.tmcommonkotlin.InputValidation
import com.example.tmcommonkotlin.logz
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


fun <T> convertRXToLiveData (observable: Observable<T>): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(observable.toFlowable(BackpressureStrategy.DROP))
}

fun <T> PublishSubject<T>.toLiveData(): LiveData<T> {
    return convertRXToLiveData(this)
}



@Throws(IOException::class)
fun Context.createImageFile(): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val x = File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    )
    return x
}

fun getDrawableUri(drawableID:Int): Uri? {
    val resources = App.getResources();
    return Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(resources.getResourcePackageName(drawableID))
        .appendPath(resources.getResourceTypeName(drawableID))
        .appendPath(resources.getResourceEntryName(drawableID))
        .build()
}

fun ImageView.easyPicasso(endpoint:String) {
    com.squareup.picasso.Picasso.get()
        .load(endpoint)
        .placeholder(R.drawable.image_not_found_yet)
        .error(R.drawable.image_not_available)
        .into(this)
}

fun ImageView.easyPicasso(uriTask:Task<Uri>?) {
    uriTask?.addOnSuccessListener { url ->
        this.easyPicasso(url.toString())
    }
    if (uriTask==null) {
        val x = getDrawableUri(R.drawable.image_not_found_yet)
        this.setImageURI(x)
    }
}

@Throws(Exception::class)
fun createUniqueID(): String? {
    return UUID.randomUUID().toString().replace("-", "").toUpperCase()
}



// This is a silly hack to make LiveData act as a life-cycle aware non-replaying observable.
// In the future, when a good life-cycle aware non-replaying observable exists, this will be unnecessary.
fun <T> LiveData<T>.onlyNew(lifecycleOwner: LifecycleOwner): LiveData<T> {
    if (this.value != null) {
        var x = Flowable.fromPublisher(LiveDataReactiveStreams.toPublisher(lifecycleOwner, this))
        x = x.skip(1)
        return LiveDataReactiveStreams.fromPublisher(x)
    }
    return this
}

internal class BlankVM : ViewModel() {}


fun handleInputValidationResult(
    validationResult: InputValidation.Result,
    layout: TextInputLayout,
    bClearError: Boolean = false
): Boolean {
    if (bClearError) {
        layout.isErrorEnabled = false
        return false
    }
    return when (validationResult) {
        is InputValidation.Result.Error -> {
            logz("error.validationResult.msg: ${validationResult.msg}")
//            layout.setErrorTextAppearance(R.style.ErrorText)
//            layout.error = validationResult.msg
            layout.error = "SDGDSFSDF" // TODO
            true
        }
        is InputValidation.Result.Warning -> {
            logz("warning")
            layout.setErrorTextAppearance(R.style.WarningText)
            layout.error = validationResult.msg
            false
        }
        is InputValidation.Result.Success -> {
            logz("success")
            layout.editText?.setText(validationResult.correctedValue)
            layout.isErrorEnabled = false
            false
        }
    }
}