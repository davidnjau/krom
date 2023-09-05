package com.dave.krom.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dave.krom.R
import com.dave.krom.adapters.AnimeAdapter
import com.dave.krom.data.DbAnimeDataList
import com.dave.krom.network_request.requests.RetrofitCalls
import com.dave.krom.viewmodels.AnimeViewModel
import com.squareup.picasso.Picasso
import java.util.ArrayList
import kotlin.random.Random


class FragmentHome : Fragment() {

    private lateinit var rootView: View
    private lateinit var viewModel: AnimeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageView: ImageView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_home, container, false)
        viewModel = ViewModelProvider(this).get(AnimeViewModel::class.java)

        recyclerView = rootView.findViewById(R.id.recyclerView)
        imageView = rootView.findViewById(R.id.imageView)
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout)

        swipeRefreshLayout.setOnRefreshListener {
            // Perform your refresh operation here, e.g., fetch new data
            fetchData()

            swipeRefreshLayout.isRefreshing = false

        }


        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        // Observe the LiveData
        viewModel.getAnimeListLiveData().observe(requireActivity()) { animeList ->
            populateRecyclerView(animeList)
        }






        return rootView
    }

    private fun populateRecyclerView(dbAnimeDataList: ArrayList<DbAnimeDataList>) {

        val chatAdapter = AnimeAdapter(dbAnimeDataList, requireContext())
        recyclerView.adapter = chatAdapter

        val size = dbAnimeDataList.size
        if (size > 0){
            val randomNumber = Random.nextInt(1, size-1)
            val randomObj = dbAnimeDataList[randomNumber]
            val imageUrl = randomObj.imageUrl

            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error)
                .into(imageView);
        }

    }

    private fun fetchData() {

        RetrofitCalls().getAnimeValues(requireContext(), viewModel)

    }

}