package com.c23ps291.heiwan.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.databinding.ActivitySearchByImageBinding

class SearchByImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchByImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchByImageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.appBarSecondary.toolbar.apply {
            title = resources.getString(R.string.result)
            setNavigationOnClickListener { finish() }
        }

        binding.btnSearch.setOnClickListener { }
    }
}