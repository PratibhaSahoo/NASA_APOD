package com.example.nasaapod.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasaapod.R
import com.example.nasaapod.database.entities.Favorites
import com.example.nasaapod.databinding.FavoriteRecyclerviewItemBinding

class FavoriteRecyclerAdapter(favoritesList : List<Favorites>, context : Context) : RecyclerView.Adapter<FavoriteRecyclerAdapter.FavoriteViewHolder>() {

    val mFavoritesList = favoritesList
    val mContext = context
    inner class FavoriteViewHolder(val binding: FavoriteRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val viewBinding = FavoriteRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return mFavoritesList.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        with(holder) {
            with(mFavoritesList[position]) {
                Glide.with(mContext)
                    .load(this.hdurl)
                    .thumbnail(Glide.with(mContext).load(R.drawable.ic_launcher_background))
                    .override(600,200)
                    .fitCenter()
                    .into(binding.favoriteImage)
                binding.favoriteDate.text = this.date
                binding.favoriteTitle.text = this.title
            }
            }
    }
}