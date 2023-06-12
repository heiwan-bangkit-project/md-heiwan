package com.c23ps291.heiwan.ui.common

import com.c23ps291.heiwan.data.model.Animal

interface OnItemClickCallback {
    fun onItemClicked(animal: Animal)
}