package com.example.nasaapod.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasaapod.R
import com.example.nasaapod.database.entities.Favorites
import com.example.nasaapod.databinding.FavoriteRecyclerviewItemBinding

class FavoriteRecyclerAdapter(
    val favoritesList: List<Favorites>,
    val context: Context,
    val listener: OnItemClickListener
) : RecyclerView.Adapter<FavoriteRecyclerAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(val binding: FavoriteRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val viewBinding = FavoriteRecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return favoritesList.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        with(holder) {
            with(favoritesList[position]) {
                Glide.with(context)
                    .load(this.hdurl)
                    .thumbnail(Glide.with(context).load(R.drawable.ic_launcher_background))
                    .override(600, 200)
                    .fitCenter()
                    .into(binding.favoriteImage)
                binding.favoriteDate.text = this.date
                binding.favoriteTitle.text = this.title

                binding.favoriteRecyclerviewItem.setOnClickListener {
                    listener.onItemClick(this.date)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(date: String)
    }
}