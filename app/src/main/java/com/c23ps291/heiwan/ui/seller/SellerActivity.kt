package com.c23ps291.heiwan.ui.seller

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.databinding.ActivitySellerBinding


class SellerActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivitySellerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBarSecondary.toolbar.setNavigationOnClickListener { finish() }
        binding.ibChat.setOnClickListener(this)

        binding.rvAnimal.layoutManager = GridLayoutManager(this, 2)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_chat -> {}
        }
    }

}