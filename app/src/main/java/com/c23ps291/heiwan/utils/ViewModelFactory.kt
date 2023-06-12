package com.c23ps291.heiwan.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c23ps291.heiwan.di.Injection
import com.c23ps291.heiwan.ui.detail.DetailViewModel
import com.c23ps291.heiwan.ui.home.HomeViewModel
import com.c23ps291.heiwan.ui.search.SearchViewModel
import com.c23ps291.heiwan.ui.seller.product.crudproduct.AddUpdateProductViewModel
import com.c23ps291.heiwan.ui.seller.product.listproduct.ListProductViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                return HomeViewModel(Injection.provideRepository(context)) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                return DetailViewModel(Injection.provideRepository(context)) as T
            }

            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                return SearchViewModel(Injection.provideRepository(context)) as T
            }

            modelClass.isAssignableFrom(AddUpdateProductViewModel::class.java) -> {
                return AddUpdateProductViewModel(Injection.provideRepository(context)) as T
            }

            modelClass.isAssignableFrom(ListProductViewModel::class.java) -> {
                return ListProductViewModel(Injection.provideRepository(context)) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}