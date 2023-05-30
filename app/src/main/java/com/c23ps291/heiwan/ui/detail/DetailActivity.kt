package com.c23ps291.heiwan.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import coil.load
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.data.model.Animal
import com.c23ps291.heiwan.databinding.ActivityDetailBinding
import com.c23ps291.heiwan.ui.seller.SellerActivity
import com.c23ps291.heiwan.utils.Resource
import com.c23ps291.heiwan.utils.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
            btnBookmark.setOnClickListener {
                Toast.makeText(
                    this@DetailActivity,
                    R.string.upcoming_feature,
                    Toast.LENGTH_SHORT
                ).show()
            }
            seller.setOnClickListener {
//                val intent = Intent(this@DetailActivity, SellerActivity::class.java)
//                startActivity(intent)
                Toast.makeText(
                    this@DetailActivity,
                    R.string.upcoming_feature,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getData() {
        detailViewModel.getListAnimal().observe(this) {
            when (it) {
                is Resource.Loading -> showLoadingState(true)
                is Resource.Success -> {
                    showLoadingState(false)
                    val data = it.data
                    if (data != null) {
                        //populateData(data)
                    }
                }

                is Resource.Error -> {
                    showLoadingState(false)
                    Toast.makeText(
                        this@DetailActivity,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }
    }

    private fun showLoadingState(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun populateData(data: Animal) {

        binding.apply {
            tvAnimalName.text = data.name
            tvDescription.text = data.description
//            ivAnimal.load(data.photoUrl) {
//                crossfade(true)
//                placeholder(R.drawable.placeholder_img)
//                error(R.drawable.placeholder_img)
//            }
        }

    }

}