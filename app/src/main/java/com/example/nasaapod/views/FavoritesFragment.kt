package com.example.nasaapod.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nasaapod.database.roomDB.ApodRoomDatabase
import com.example.nasaapod.databinding.FragmentFavoritesBinding
import com.example.nasaapod.networkManager.ApodRepository
import com.example.nasaapod.networkManager.RetrofitHelper
import com.example.nasaapod.viewmodel.ApodViewModel
import com.example.nasaapod.views.adapter.FavoriteRecyclerAdapter

class FavoritesFragment : Fragment() {

    private lateinit var fragmentFavoritesBinding: FragmentFavoritesBinding
    private lateinit var apodViewModel: ApodViewModel
    private lateinit var favoriteRecyclerAdapter : FavoriteRecyclerAdapter
    lateinit var apodRepository: ApodRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFavoritesBinding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return fragmentFavoritesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofitService = RetrofitHelper.apodApi
        val apodRoomDatabase = ApodRoomDatabase.getDBInstance(requireActivity().applicationContext)
        apodRepository = ApodRepository(retrofitService, apodRoomDatabase, requireActivity().applicationContext)
        apodViewModel = ApodViewModel("null", apodRepository)
        getAllFavorites()
    }

    private fun getAllFavorites() {
        var favoritesize: Int?
        apodViewModel.getFavoritesList().observe(viewLifecycleOwner) {
            favoritesize = it.size
            favoriteRecyclerAdapter = FavoriteRecyclerAdapter(it, requireActivity().applicationContext)
            fragmentFavoritesBinding.favoriteRecyclerview.adapter = favoriteRecyclerAdapter
        }
    }
}