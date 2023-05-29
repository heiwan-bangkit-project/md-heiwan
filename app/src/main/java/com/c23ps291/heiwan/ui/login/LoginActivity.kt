package com.c23ps291.heiwan.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.databinding.ActivityLoginBinding
import com.c23ps291.heiwan.ui.MainActivity
import com.c23ps291.heiwan.ui.register.RegisterActivity
import com.c23ps291.heiwan.ui.setting.SettingViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity(), OnClickListener {


    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private val settingViewModel: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        initTheme()

        binding.btnLogin.setOnClickListener(this)
        binding.btnGoToRegister.setOnClickListener(this)
    }

    private fun reload() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload()
        }
    }

    private fun validation() {
        binding.apply {
            when {
                (edtEmail.text?.isNotEmpty()!! && edtPassword.text?.isNotEmpty()!!) -> {
                    val email = edtEmail.text.toString()
                    val pass = edtPassword.text.toString()
                    login(email, pass)
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
        }

    }
    private fun login(email: String, password: String) {
        showLoadingState(true)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    reload()
                    showLoadingState(false)
                    clearTextField()
                } else {
                    Snackbar.make(
                        binding.root as ViewGroup,
                        R.string.auth_failed,
                        Snackbar.LENGTH_LONG
                    ).show()
                    showLoadingState(false)
                }
            }
    }

    private fun showLoadingState(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun initTheme() {
        settingViewModel.getThemeSettings().observe(this) { theme: Int ->
            when (theme) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    settingViewModel.saveThemeSetting(0)
                }

                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    settingViewModel.saveThemeSetting(1)
                }

                else -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    settingViewModel.saveThemeSetting(2)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                validation()
            }
            R.id.btn_go_to_register -> {
                startActivity(
                    Intent(this@LoginActivity, RegisterActivity::class.java)
                )
            }
        }
    }

}