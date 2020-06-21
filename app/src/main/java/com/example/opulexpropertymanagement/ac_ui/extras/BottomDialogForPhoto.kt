package com.example.opulexpropertymanagement.ac_ui.extras

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.grocerygo.activities_and_frags.Inheritables.TMActivity
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.ac_ui.activities.ActivityHostInterface
import com.example.opulexpropertymanagement.app.Config
import com.example.tmcommonkotlin.logz
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet.*

class BottomDialogForPhoto(val action:(intent: Intent?)->Unit) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet, container, false)
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
            logz("btn_camera")
            (activity as TMActivity).tryPermissionAction(
                permissions = arrayOf(
                    Manifest.permission.CAMERA
                ),
                code = Config.CODE_CAMERA,
                startAction = {
                    activity?.startActivityForResult(
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                        Config.CODE_CAMERA
                    )
                },
                resultHandlingAction = action
            )
            closeMyself()
        }
        btn_gallery.setOnClickListener {
            logz("btn_gallery")
            (activity as TMActivity).tryPermissionAction(
                permissions = arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                code = Config.CODE_PICK_IMAGE,
                startAction = (activity as ActivityHostInterface).pickImage,
                resultHandlingAction = action
            )
            closeMyself()
        }
    }
}