package com.c23ps291.heiwan.ui.search

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.databinding.ActivitySearchByImageBinding
import com.c23ps291.heiwan.ui.detail.DetailActivity
import com.c23ps291.heiwan.utils.Resource
import com.c23ps291.heiwan.utils.ViewModelFactory
import com.c23ps291.heiwan.utils.reduceFileImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class SearchByImageActivity : AppCompatActivity() {

    private var animalName: String? = ""
    private lateinit var binding: ActivitySearchByImageBinding
    private val searchViewModel: SearchViewModel by viewModels {
        ViewModelFactory(this)
    }
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
                postData()
            }

            btnSearch.setOnClickListener {

                if (animalName?.isNotEmpty() == true) {
                    val intent = Intent(this@SearchByImageActivity, SearchActivity::class.java)
                    intent.putExtra(SearchActivity.SEARCH_ANIMAL, animalName)
                    startActivity(intent)
                }
            }

        }

    }

    private fun postData() {
        val currentImg = searchImg?.let { File(it) }
        val file = currentImg?.let { reduceFileImage(it) }

        val requestImageFile = file!!.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "image",
            file.name,
            requestImageFile
        )

        searchViewModel.getPred(imageMultipart).observe(this@SearchByImageActivity) {
            when (it) {
                is Resource.Loading -> showLoadingState(true)
                is Resource.Success -> {
                    showLoadingState(false)
                    binding.tvAnimalName.text = it.data?.data ?: ""
                    animalName = it.data?.data
                }
                is Resource.Error -> {
                    showLoadingState(false)
                    Toast.makeText(
                        this@SearchByImageActivity,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun showLoadingState(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}