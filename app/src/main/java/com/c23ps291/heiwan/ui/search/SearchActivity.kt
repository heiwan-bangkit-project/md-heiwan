package com.c23ps291.heiwan.ui.search

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.data.model.Animal
import com.c23ps291.heiwan.databinding.ActivitySearchBinding
import com.c23ps291.heiwan.ui.common.ImageChooseBottomSheetFragment
import com.c23ps291.heiwan.ui.common.adapter.AnimalAdapter
import com.c23ps291.heiwan.ui.detail.DetailViewModel
import com.c23ps291.heiwan.utils.Resource
import com.c23ps291.heiwan.utils.ViewModelFactory


class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val searchViewModel: SearchViewModel by viewModels {
        ViewModelFactory(this)
    }
    private lateinit var modalBottomSheet: ImageChooseBottomSheetFragment
    private lateinit var animalAdapter: AnimalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        modalBottomSheet = ImageChooseBottomSheetFragment()

        binding.rvAnimal.layoutManager = GridLayoutManager(this, 2)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initSearchBar()

        binding.apply {
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    Toast.makeText(this@SearchActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    getData(searchView.text.toString())
                    showLoadingState(true)
                    false
                }
        }
    }

    private fun initSearchBar() {
        binding.apply {
            searchView.show()
            searchView.inflateMenu(R.menu.search_menu)
            searchView.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.navigation_camera -> {
                        modalBottomSheet.show(
                            supportFragmentManager,
                            ImageChooseBottomSheetFragment.TAG
                        )
                        true
                    }
                    else -> super.onOptionsItemSelected(menuItem)
                }
            }
        }
    }

    private fun getData(name: String) {
        searchViewModel.getListAnimal(name).observe(this@SearchActivity) {
            when (it) {
                is Resource.Loading -> showLoadingState(true)
                is Resource.Success -> {
                    showLoadingState(false)
                    it.data?.data?.let { it1 -> populateData(it1) }
                }
                is Resource.Error -> {
                    showLoadingState(false)
                    Toast.makeText(
                        this@SearchActivity,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun populateData(data: List<Animal>) {
        animalAdapter = AnimalAdapter(data)
        binding.rvAnimal.apply {
            layoutManager = GridLayoutManager(this@SearchActivity, 2)
            setHasFixedSize(true)
            adapter = animalAdapter
        }
    }

    private fun showLoadingState(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}