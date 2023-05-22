package com.c23ps291.heiwan.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.databinding.ActivityRegisterBinding
import com.c23ps291.heiwan.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener(this)
        binding.btnGoToLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register -> {}
            R.id.btn_go_to_login -> finish()
        }
    }
}