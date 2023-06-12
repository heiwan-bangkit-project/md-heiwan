package com.c23ps291.heiwan.ui.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.data.model.Animal
import com.c23ps291.heiwan.databinding.ItemAnimalBinding
import com.c23ps291.heiwan.ui.common.OnItemClickCallback

class AnimalAdapter(
    private val listAnimal: List<Animal>,
    private val onItemClickCallback: OnItemClickCallback,
) : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val item = ItemAnimalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimalViewHolder(item)
    }

    override fun getItemCount(): Int = listAnimal.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.bind(listAnimal[position])
    }

    inner class AnimalViewHolder(private val binding: ItemAnimalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Animal) {
            binding.apply {
                tvAnimal.text = data.name
                ivAnimal.load(data.image) {
                    crossfade(true)
                    placeholder(R.drawable.placeholder_img)
                    error(R.drawable.placeholder_img)
                }
            }

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(data)
            }
        }
    }
}