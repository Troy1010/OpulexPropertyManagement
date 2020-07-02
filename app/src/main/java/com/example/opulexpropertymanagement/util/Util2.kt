package com.example.opulexpropertymanagement.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.navigation.NavController
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
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0

// For some reason, if this file is named Util, tests do not compile due to: Unresolved reference



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

fun ImageView.easyPicasso(uriTask: Task<Uri>?) {
    uriTask?.addOnSuccessListener { url ->
        this.easyPicasso(url.toString())
    }
    if (uriTask==null) {
        val x = getDrawableUri(R.drawable.image_not_found_yet)
        this.setImageURI(x)
    }
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


inline fun <reified T:ViewModel> AppCompatActivity.scopeVMToDestinations(
    navController: NavController,
    destinations: HashSet<Int>
) {
    navController.addOnDestinationChangedListener { _, destination, _ ->
        if (destination.id !in destinations) {
            this.viewModelStore.remove<T>()
        }
    }
}

inline fun <reified T> ViewModelStore.remove() {
    val VMStoreField = ViewModelStore::class.java.getDeclaredField("mMap")
    VMStoreField.isAccessible = true
    val VMStoreMap = VMStoreField.get(this) as HashMap<*, ViewModel>
    var keyToDelete:String?=null
    VMStoreMap.map {
        if (it.value is T) {
            keyToDelete = it.key.toString()
        }
        it
    }
    if (keyToDelete!=null) {
        val methodField = ViewModel::class.java.getDeclaredMethod("onCleared")
        methodField.isAccessible = true
        methodField.invoke(VMStoreMap[keyToDelete])
        VMStoreMap.remove(keyToDelete)
    }
}

@Throws(Exception::class)
fun generateUniqueID(): String? {
    return UUID.randomUUID().toString().replace("-", "").toUpperCase()
}


fun <T> convertRXToLiveData (observable: Observable<T>): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(observable.toFlowable(BackpressureStrategy.DROP))
}

fun <T> PublishSubject<T>.toLiveData(): LiveData<T> {
    return convertRXToLiveData(this)
}