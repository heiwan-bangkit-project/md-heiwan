package com.c23ps291.heiwan.ui.common.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.data.local.entity.AnimalEntity
import com.c23ps291.heiwan.databinding.ItemAnimalBinding
import com.c23ps291.heiwan.ui.detail.DetailActivity

class AnimalListAdapter :
    PagingDataAdapter<AnimalEntity, AnimalListAdapter.StoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val itemAnimalBinding =
            ItemAnimalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(itemAnimalBinding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }

    }

    class StoryViewHolder(private val binding: ItemAnimalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: AnimalEntity) {
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

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AnimalEntity>() {
            override fun areItemsTheSame(oldItem: AnimalEntity, newItem: AnimalEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: AnimalEntity,
                newItem: AnimalEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}