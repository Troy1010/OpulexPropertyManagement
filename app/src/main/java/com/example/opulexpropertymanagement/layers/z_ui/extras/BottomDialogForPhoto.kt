package com.example.opulexpropertymanagement.layers.z_ui.extras

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import com.example.grocerygo.activities_and_frags.Inheritables.TMActivity
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.layers.z_ui.activities.ActivityHostInterface
import com.example.opulexpropertymanagement.app.Config
import com.example.opulexpropertymanagement.util.createImageFile
import com.example.tmcommonkotlin.easyToast
import com.example.tmcommonkotlin.logz
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import java.io.File
import java.io.IOException


class BottomDialogForPhoto(val contextZ: Context, val title:String?=null, val actionForUri:(uri: Uri?, choice:Choice)->Unit) : BottomSheetDialogFragment() {
    lateinit var pictureUri: Uri


    enum class Choice {
        TakePicture, Gallery
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.bottom_sheet, container, false)
        if (title==null) {
            v.textview_title.visibility=View.GONE
        } else {
            v.textview_title.text = title
        }
        return v
    }

    fun closeMyself() {
        activity?.let { activity ->
            for (fragment in activity.supportFragmentManager.fragments) {
                if (fragment is BottomDialogForPhoto) {
                    activity.supportFragmentManager.beginTransaction().remove(fragment).commit()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        btn_camera.setOnClickListener {
            (activity as TMActivity).tryPermissionAction(
                permissions = arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                code = Config.CODE_CAMERA,
                startAction = {
                    val photoFile: File? = try {
                        contextZ.createImageFile()
                    } catch (ex: IOException) {
                        contextZ.easyToast("Error creating image file")
                        logz("error while creating file:$ex")
                        null
                    }
                    photoFile?.also { file ->
                        pictureUri = FileProvider.getUriForFile(
                            contextZ,
                            "com.example.opulexpropertymanagement.fileprovider",
                            file
                        )
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri)
                        activity?.startActivityForResult(
                            intent,
                            Config.CODE_CAMERA
                        )
                    }
                },
                resultHandlingAction = { intent -> actionForUri(pictureUri, Choice.TakePicture) }
            )
            closeMyself()
        }
        btn_gallery.setOnClickListener {
            (activity as TMActivity).tryPermissionAction(
                permissions = arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                code = Config.CODE_PICK_IMAGE,
                startAction = (activity as ActivityHostInterface).pickImage,
                resultHandlingAction = { intent -> actionForUri(intent?.data, Choice.Gallery) }
            )
            closeMyself()
        }
    }
}