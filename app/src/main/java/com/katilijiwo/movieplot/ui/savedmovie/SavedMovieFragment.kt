package com.katilijiwo.movieplot.ui.savedmovie

import android.os.Bundle
import android.view.View
import com.katilijiwo.movieplot.R
import com.katilijiwo.movieplot.base.BaseFragment
import com.katilijiwo.movieplot.databinding.FragmentSavedMovieBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SavedMovieFragment: BaseFragment<FragmentSavedMovieBinding>(
    R.layout.fragment_saved_movie
) {

    private val vieModel: SavedMovieViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vieModel.savedMovies.observe(viewLifecycleOwner, {
            val mantap = it
        })

    }
}