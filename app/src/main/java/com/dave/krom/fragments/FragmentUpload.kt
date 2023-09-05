package com.dave.krom.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dave.krom.R
import com.dave.krom.adapters.AnimeAdapter
import com.dave.krom.network_request.requests.RetrofitCalls
import com.dave.krom.viewmodels.AnimeViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.create
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream


class FragmentUpload : Fragment() {

    private lateinit var rootView: View
    private lateinit var imageView:ImageView

    private lateinit var viewModel: AnimeViewModel
    private lateinit var recyclerView: RecyclerView

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_upload, container, false)

        imageView = rootView.findViewById(R.id.imageView)

        viewModel = ViewModelProvider(this).get(AnimeViewModel::class.java)

        recyclerView = rootView.findViewById(R.id.recyclerView)

        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL )

        val dbAnimeDataList = viewModel.getAnimeList()

        val chatAdapter = AnimeAdapter(dbAnimeDataList, requireContext())
        recyclerView.adapter = chatAdapter

        rootView.findViewById<RelativeLayout>(R.id.relativeLayout).setOnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }


        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                val uri: Uri = data?.data!!
                val imageFile = uri.toFile()

                // Use Uri object instead of File to avoid storage permissions
                imageView.setImageURI(uri)

                RetrofitCalls().upload(requireContext(), imageFile)


            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

}