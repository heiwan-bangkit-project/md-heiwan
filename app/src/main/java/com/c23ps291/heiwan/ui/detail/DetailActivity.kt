package com.c23ps291.heiwan.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}