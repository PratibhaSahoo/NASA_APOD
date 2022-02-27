package com.example.nasaapod.views

import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.nasaapod.R
import com.example.nasaapod.database.entities.Favorites
import com.example.nasaapod.database.roomDB.ApodRoomDatabase
import com.example.nasaapod.databinding.FragmentMainBinding
import com.example.nasaapod.networkManager.ApodRepository
import com.example.nasaapod.networkManager.RetrofitHelper
import com.example.nasaapod.viewmodel.ApodViewModel
import com.example.nasaapod.viewmodel.ApodViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {

    private lateinit var apodViewModel: ApodViewModel
    private var fragmentMainBinding: FragmentMainBinding? = null
    private lateinit var navController: NavController
    private lateinit var viewModelFactory: ApodViewModelFactory
    val args: MainFragmentArgs by navArgs()
    private lateinit var favorites: Favorites
    lateinit var apodRepository: ApodRepository
    private lateinit var date: String

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)
        return fragmentMainBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
        initObservers(view)

    }

    private fun initUI(view: View) {
        navController = Navigation.findNavController(view)

        if (args.date == "default") {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val todayDate = simpleDateFormat.format(Date())
            date = todayDate.toString()
        } else {
            date = args.date
        }
        fragmentMainBinding!!.toolbar.title = date

        val retrofitService = RetrofitHelper.apodApi
        val apodRoomDatabase = ApodRoomDatabase.getDBInstance(requireActivity().applicationContext)
        apodRepository =
            ApodRepository(retrofitService, apodRoomDatabase, requireActivity().applicationContext)

        viewModelFactory = ApodViewModelFactory(date, apodRepository)
        apodViewModel = ViewModelProvider(this, viewModelFactory).get(ApodViewModel::class.java)
    }

    private fun initObservers(view: View) {

        apodViewModel.apodLivedata.observe(viewLifecycleOwner) {
            if (it.body()!!.media_type == "image") {
                val requestOptions = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(this)
                    .load(it.body()!!.hdurl)
                    .thumbnail(
                        Glide.with(requireActivity()).load(R.drawable.ic_loading)
                    )
                    .apply(requestOptions)
                    .fitCenter()
                    .into(fragmentMainBinding!!.image)
                favorites = Favorites(
                    args.date,
                    it.body()!!.explanation!!,
                    it.body()!!.hdurl!!,
                    it.body()!!.title!!
                )
            } else {
                fragmentMainBinding!!.apply {
                    image.visibility = View.GONE
                    webView.apply {
                        visibility = View.VISIBLE
                        settings.javaScriptEnabled = true
                        settings.pluginState = WebSettings.PluginState.ON
                        loadUrl(it.body()!!.url!!)
                        webChromeClient = WebChromeClient()
                    }
                }
                favorites = Favorites(
                    args.date,
                    it.body()!!.explanation!!,
                    it.body()!!.url!!,
                    it.body()!!.title!!
                )
            }
            fragmentMainBinding!!.apply {
                title.text = it.body()!!.title
                explanation.text = it.body()!!.explanation
                addToFavorites.setOnClickListener {
                    apodViewModel.insertFavorite(favorites)
                    Snackbar.make(view, getString(R.string.added_to_favorites), Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuId: Int = item.itemId
        when (menuId) {
            R.id.calendar -> {
                navController.navigate(R.id.action_mainFragment_to_calendarFragment)
            }

            R.id.favorite -> {
                navController.navigate(R.id.action_mainFragment_to_favoritesFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}