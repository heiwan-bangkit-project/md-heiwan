package com.c23ps291.heiwan.ui.seller.product

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.databinding.ActivityAddUpdateProductBinding
import com.c23ps291.heiwan.ui.search.SearchByImageActivity
import com.c23ps291.heiwan.utils.uriToFile
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File

class AddUpdateProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateProductBinding
    private var photoFile: File? = null
    private var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnUploadChangeText: String
        val btnAddUpdateText: String
        if (isEdit) {
            btnUploadChangeText = resources.getString(R.string.btn_text_change_image)
            btnAddUpdateText = resources.getString(R.string.text_btn_update)
            editMode()
        } else {
            btnUploadChangeText = resources.getString(R.string.btn_text_upload_image)
            btnAddUpdateText = resources.getString(R.string.text_btn_add)
            addMode()
        }

        binding.apply {
            btnUploadChangePhoto.text = btnUploadChangeText
            btnAddUpdate.text = btnAddUpdateText

            btnUploadChangePhoto.setOnClickListener {
                startGallery()
            }
        }


    }

    private fun editMode() {
        binding.toolbar.inflateMenu(R.menu.delete_menu)

        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_delete) {
                showAlertDialog()
            }
            false
        }


    }

    private fun showAlertDialog() {
        MaterialAlertDialogBuilder(this)
            .setCancelable(false)
            .setIcon(ContextCompat.getDrawable(this, R.drawable.ic_delete))
            .setTitle(resources.getString(R.string.text_menu_delete))
            .setMessage(resources.getString(R.string.text_dialog_msg_delete))
            .setNegativeButton(resources.getString(R.string.text_no)) { dialog, which ->
                Toast.makeText(this, getString(R.string.text_no), Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton(resources.getString(R.string.text_yes)) { dialog, which ->
                Toast.makeText(this, getString(R.string.text_yes), Toast.LENGTH_SHORT)
                    .show()
            }
            .show()
    }

    private fun addMode() {

    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.choose_a_pic))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            photoFile = myFile
            binding.ivAnimal.setImageURI(selectedImg)
        }
    }


    companion object {
        const val TAG = "AddUpdateProduct"
        private const val REQUEST_CODE_PERMISSIONS = 10
    }


}