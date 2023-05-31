package com.c23ps291.heiwan.ui.seller.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.databinding.ActivityListProductBinding

class ListProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            fabAdd.setOnClickListener {
                val intent = Intent(this@ListProductActivity, AddUpdateProductActivity::class.java)
                startActivity(intent)
            }
        }
    }
}