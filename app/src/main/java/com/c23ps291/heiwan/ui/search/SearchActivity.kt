package com.c23ps291.heiwan.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.databinding.ActivitySearchBinding
import com.c23ps291.heiwan.ui.common.ImageChooseBottomSheetFragment


class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var modalBottomSheet: ImageChooseBottomSheetFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        modalBottomSheet = ImageChooseBottomSheetFragment()

        binding.rvAnimal.layoutManager = GridLayoutManager(this, 2)

        initSearchBar()
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
}