package com.c23ps291.heiwan.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.databinding.ActivityLoginBinding
import com.c23ps291.heiwan.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity(), OnClickListener {


    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener(this)
        binding.btnGoToRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {}
            R.id.btn_go_to_register -> {
                startActivity(
                    Intent(this@LoginActivity, RegisterActivity::class.java)
                )
            }
        }
    }

}