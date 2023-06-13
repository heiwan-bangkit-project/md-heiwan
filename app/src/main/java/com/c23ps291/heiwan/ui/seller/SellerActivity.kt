package com.c23ps291.heiwan.ui.seller

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.data.model.Animal
import com.c23ps291.heiwan.databinding.ActivitySellerBinding
import com.c23ps291.heiwan.ui.common.OnItemClickCallback
import com.c23ps291.heiwan.ui.common.adapter.AnimalAdapter
import com.c23ps291.heiwan.ui.detail.DetailActivity
import com.c23ps291.heiwan.ui.seller.product.listproduct.ListProductViewModel
import com.c23ps291.heiwan.utils.Resource
import com.c23ps291.heiwan.utils.ViewModelFactory


class SellerActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivitySellerBinding
    private val viewModel: ListProductViewModel by viewModels {
        ViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBarSecondary.toolbar.setNavigationOnClickListener { finish() }
        binding.ibChat.setOnClickListener(this)

        binding.rvAnimal.layoutManager = GridLayoutManager(this, 2)

        val id = intent.getStringExtra(EXTRA_UUID)

        if (id != null) {
            getDataProducts(id)
            getDataSeller(id)
        }

    }

    private fun getDataSeller(uuid: String) {
        viewModel.getSellerByID(uuid).observe(this@SellerActivity) {
            when (it) {
                is Resource.Loading -> showLoadingState(true)
                is Resource.Success -> {
                    showLoadingState(false)
                    val seller = it.data?.seller?.get(0)
                    binding.tvSellerName.text = seller?.name ?: "-"
                }

                is Resource.Error -> {
                    showLoadingState(false)
                    Toast.makeText(
                        this@SellerActivity,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getDataProducts(id: String) {
        viewModel.getProducts(id).observe(this@SellerActivity) {
            when (it) {
                is Resource.Loading -> showLoadingState(true)
                is Resource.Success -> {
                    showLoadingState(false)
                    val result = it.data?.data
                    if (result?.isNotEmpty() == true) {
                        populateData(result)
                    } else {

                    }
                }

                is Resource.Error -> {
                    showLoadingState(false)
                    Toast.makeText(
                        this@SellerActivity,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun populateData(data: List<Animal>) {

        val animalAdapter = AnimalAdapter(data, object : OnItemClickCallback {
            override fun onItemClicked(animal: Animal) {
                val intent = Intent(this@SellerActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, animal.id)
                startActivity(intent)
            }

        })
        binding.rvAnimal.apply {
            layoutManager = GridLayoutManager(this@SellerActivity, 2)
            setHasFixedSize(true)
            adapter = animalAdapter
        }
    }

    private fun showLoadingState(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_chat -> {
                Toast.makeText(
                    this@SellerActivity,
                    R.string.upcoming_feature,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        const val EXTRA_UUID = "extra_uuid"
    }
}