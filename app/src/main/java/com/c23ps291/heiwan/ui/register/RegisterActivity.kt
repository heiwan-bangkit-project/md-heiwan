package com.c23ps291.heiwan.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.databinding.ActivityRegisterBinding
import com.c23ps291.heiwan.ui.MainActivity
import com.c23ps291.heiwan.ui.login.LoginActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnRegister.setOnClickListener(this)
        binding.btnGoToLogin.setOnClickListener(this)

    }

    public override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if(currentUser != null){
            reload()
        }
    }

    private fun reload() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun validation() {
        binding.apply {
            when {
                (edtUsername.text?.isNotEmpty()!! && edtEmail.text?.isNotEmpty()!! &&
                        edtPassword.text?.isNotEmpty()!!) -> {

                    val username = edtUsername.text.toString()
                    val email = edtEmail.text.toString()
                    val pass = edtPassword.text.toString()

                    register(username, email, pass)

                }

                else -> Snackbar.make(
                    binding.root as ViewGroup,
                    R.string.fill_the_text_field,
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }
    }

    private fun clearTextField() {
        binding.apply {
            edtEmail.setText("")
            edtPassword.setText("")
            edtPassword.setText("")
        }

    }

    private fun register(username: String, email: String, password: String) {
        showLoadingState(true)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = Firebase.auth.currentUser

                    if (user != null) {
                        val profileUpdates = userProfileChangeRequest {
                            displayName = username
                        }
                        user.updateProfile(profileUpdates)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    reload()
                                }
                            }

                        showLoadingState(false)
                        Snackbar.make(
                            binding.root as ViewGroup,
                            R.string.success,
                            Snackbar.LENGTH_LONG
                        ).show()
                        clearTextField()
                    }

                } else {
                    showLoadingState(false)
                    Snackbar.make(
                        binding.root as ViewGroup,
                        R.string.auth_failed,
                        Snackbar.LENGTH_LONG
                    ).show()

                }
            }
    }

    private fun showLoadingState(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register -> {
                validation()
            }
            R.id.btn_go_to_login -> finish()
        }
    }
}