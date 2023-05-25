package com.c23ps291.heiwan.ui.search

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.databinding.ActivitySearchByImageBinding


class SearchByImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchByImageBinding
    private var searchImg: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchByImageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        searchImg = intent.getStringExtra("BitmapPath")
        binding.appBarSecondary.toolbar.apply {
            title = resources.getString(R.string.result)
            setNavigationOnClickListener { finish() }
        }

        binding.apply {
            if (searchImg != null) {
                val result = BitmapFactory.decodeFile(searchImg)
                ivAnimal.setImageBitmap(result)
            }

        }
    }
}