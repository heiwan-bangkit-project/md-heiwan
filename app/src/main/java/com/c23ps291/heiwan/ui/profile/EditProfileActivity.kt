package com.c23ps291.heiwan.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.databinding.ActivityEditProfileBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class EditProfileActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.appBarSecondary.toolbar.setNavigationOnClickListener { finish() }

        binding.btnEdit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_edit -> {
                validation()
            }
        }
    }

    private fun validation() {
        binding.apply {
            when {
                (edtUsername.text?.isNotEmpty()!!) -> {
                    updateProfile()
                }

                else -> Snackbar.make(
                    binding.root as ViewGroup,
                    R.string.fill_the_text_field,
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }
    }

    private fun updateProfile() {
        val currentUser = auth.currentUser
        val username: String

        binding.apply {
            username = edtUsername.text.toString()
        }

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .build()

        currentUser!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        R.string.success,
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                } else {
                    Log.d("Profile", "User profile updated.")
                }
            }
    }

}