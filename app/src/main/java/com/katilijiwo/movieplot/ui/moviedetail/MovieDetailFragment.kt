package com.katilijiwo.movieplot.ui.moviedetail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.katilijiwo.movieplot.R
import com.katilijiwo.movieplot.base.BaseFragment
import com.katilijiwo.movieplot.data.local.entity.Movie
import com.katilijiwo.movieplot.databinding.FragmentMovieDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MovieDetailFragment: BaseFragment<FragmentMovieDetailBinding>(
    R.layout.fragment_movie_detail
) {

    private val INFORMATION = "Informasi"
    private val COMMENT = "Komentar"
    private val args: MovieDetailFragmentArgs by navArgs()
    lateinit var movieDetailStateAdapter: MovieDetailStateAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupTabLayout()
    }

    private fun setupViewPager() {
        movieDetailStateAdapter = MovieDetailStateAdapter(this, args.movieId)
        binding.vpMovieDetail.adapter = movieDetailStateAdapter
    }

    private fun setupTabLayout() {
        TabLayoutMediator(binding.tlMovieDetail, binding.vpMovieDetail) { tab, position ->
            when(position){
                0 -> tab.text = INFORMATION
                1 -> tab.text = COMMENT
            }
        }.attach()
    }
}