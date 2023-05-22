package com.c23ps291.heiwan.ui.profile

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.btnChangePhoto.setOnClickListener(this)
        binding.btnEdit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_change_photo -> {}
            R.id.btn_edit -> {}
        }
    }
}