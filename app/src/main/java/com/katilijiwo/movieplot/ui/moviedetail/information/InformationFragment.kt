package com.katilijiwo.movieplot.ui.moviedetail.information

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.katilijiwo.movieplot.R
import com.katilijiwo.movieplot.base.BaseFragment
import com.katilijiwo.movieplot.databinding.FragmentInformationBinding
import com.katilijiwo.movieplot.ui.moviedetail.MovieDetailStateAdapter
import com.katilijiwo.movieplot.ui.moviedetail.MovieDetailViewModel
import com.katilijiwo.movieplot.util.Constant.IMAGE_URL_PREFIX_500
import com.katilijiwo.movieplot.util.MovieEvent
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class InformationFragment: BaseFragment<FragmentInformationBinding>(
    R.layout.fragment_information
), View.OnClickListener {
    private val viewModel: MovieDetailViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments?.getInt(MovieDetailStateAdapter.MOVIE_ID) == null){
            showError(isFinish = true, isCancelable = false)
        } else {
            viewModel.fetchMovieDetail(arguments?.getInt(MovieDetailStateAdapter.MOVIE_ID)!!)
        }

        binding.llLoading.bringToFront()
        viewModel.setMovieDetail(MovieDetailViewModel.MovieDetail(0,"", "", "", 0.0, 0, ""))
        binding.viewModel = viewModel
    }

    override fun setListener() {
        super.setListener()

        binding.fabSaveImg.setOnClickListener(this)
        binding.fabDeleteImg.setOnClickListener(this)
        viewModel.movieDetailStatus.observe(viewLifecycleOwner, {
            when (it) {
                is MovieEvent.Success -> {
                    setProgressBarLoading(false)
                    setupImageView(it.data.backdropPath)
                }
                is MovieEvent.Error -> {
                    showError(isFinish = true, isCancelable = false)
                }
                is MovieEvent.Loading -> {
                    setProgressBarLoading(true)
                }
            }
        })

        val movieID = arguments?.getInt(MovieDetailStateAdapter.MOVIE_ID)!!
        viewModel.getMovieByID(movieID).observe(viewLifecycleOwner, {
            if (it?.id != null && it.id == movieID){
                binding.fabSaveImg.visibility = View.GONE
                binding.fabDeleteImg.visibility = View.VISIBLE
            } else {
                binding.fabSaveImg.visibility = View.VISIBLE
                binding.fabDeleteImg.visibility = View.GONE
            }
        })
    }

    private fun setupImageView(posterPath: String) {
        Glide.with(requireContext())
            .load("$IMAGE_URL_PREFIX_500$posterPath")
            .error(R.drawable.ic_baseline_broken_image_24)
            .into(binding.ivMovie)
    }

    private fun setProgressBarLoading(isLoading: Boolean){
        if(isLoading){
            binding.llLoading.visibility = View.VISIBLE
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            binding.llLoading.visibility = View.GONE
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.fab_save_img -> {
                insertMovie()
            }
            R.id.fab_delete_img -> {
                deleteMovie()
            }
        }
    }

    private fun insertMovie(){
        try {
            val dateTimeStamp = Calendar.getInstance().timeInMillis
            val bitmap = binding.ivMovie.drawable.toBitmap(binding.ivMovie.width, binding.ivMovie.height, Bitmap.Config.RGB_565)
            if(viewModel.getMovieDetail().value != null && bitmap != null && dateTimeStamp != null){
                viewModel.insertMovie(bitmap, dateTimeStamp)
                Toast.makeText(requireContext(), "Save Complete!", Toast.LENGTH_SHORT).show()
            } else {
                showError()
            }
        } catch (ex: Exception){
            showError()
        }
    }

    private fun deleteMovie(){
        try {
            if(viewModel.getMovieDetail().value != null){
                viewModel.deleteMovie()
                Toast.makeText(requireContext(), "Delete Complete!", Toast.LENGTH_SHORT).show()
            } else {
                showError()
            }
        } catch (ex: Exception){
            showError()
        }
    }

}