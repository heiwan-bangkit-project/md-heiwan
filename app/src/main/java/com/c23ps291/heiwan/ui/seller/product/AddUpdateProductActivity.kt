package com.c23ps291.heiwan.ui.seller.product

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.databinding.ActivityAddUpdateProductBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AddUpdateProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateProductBinding

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

        binding.btnUploadChangePhoto.text = btnUploadChangeText
        binding.btnAddUpdate.text = btnAddUpdateText


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


}