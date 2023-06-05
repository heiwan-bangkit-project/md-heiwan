package com.c23ps291.heiwan.ui.common.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c23ps291.heiwan.data.model.Animal
import com.c23ps291.heiwan.databinding.ItemAnimalBinding
import com.c23ps291.heiwan.ui.detail.DetailActivity

class AnimalAdapter(private val listAnimal: List<Animal>): RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val item = ItemAnimalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimalViewHolder(item)
    }

    override fun getItemCount(): Int = listAnimal.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.bind(listAnimal[position])
    }

    class AnimalViewHolder(private val binding: ItemAnimalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Animal) {
            binding.apply {
                tvAnimal.text = data.name
//                ivAnimal.load(data.photoUrl) {
//                    crossfade(true)
//                    placeholder(R.drawable.placeholder_img)
//                    error(R.drawable.placeholder_img)
//                }
            }
            itemView.setOnClickListener {
//                val optionsCompat: ActivityOptionsCompat =
//                    ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        itemView.context as Activity,
//                        Pair(binding.ivPhoto, "imageStory"),
//                    )
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, data.id)
                itemView.context.startActivity(intent)
            }
        }
    }
}