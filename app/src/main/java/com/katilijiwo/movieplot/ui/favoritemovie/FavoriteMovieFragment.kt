package com.katilijiwo.movieplot.ui.favoritemovie

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.katilijiwo.movieplot.R
import com.katilijiwo.movieplot.base.BaseFragment
import com.katilijiwo.movieplot.databinding.FragmentFavoriteMovieBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteMovieFragment: BaseFragment<FragmentFavoriteMovieBinding>(
    R.layout.fragment_favorite_movie
) {

    private lateinit var favoriteMovieAdapter: FavoriteMovieAdapter
    private val viewModel: FavoriteMovieViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    override fun setListener() {
        super.setListener()
        viewModel.savedMovies.observe(viewLifecycleOwner, {
            if(it.isNotEmpty()){
                favoriteMovieAdapter.listData = it
                favoriteMovieAdapter.notifyDataSetChanged()
                setComponent(DATA_FOUND)
            } else {
                setComponent(DATA_NOT_FOUND)
            }
        })
    }

    private fun setComponent(status: Int){
        when(status){
            DATA_FOUND -> {
                binding.tvDataStatus.visibility = View.GONE
                binding.rvSavedMovie.visibility = View.VISIBLE
            }
            DATA_NOT_FOUND -> {
                binding.tvDataStatus.visibility = View.VISIBLE
                binding.rvSavedMovie.visibility = View.GONE
            }
        }
    }

    private fun setUpRecyclerView() {
        favoriteMovieAdapter = FavoriteMovieAdapter(
            { movieID ->
                AlertDialog.Builder(requireContext())
                    .setMessage(getString(R.string.are_you_sure_want_to_delete_this_movie_from_saved_list))
                    .setCancelable(true)
                    .setPositiveButton(resources.getString(R.string.oke)){ _, _ ->
                        viewModel.deleteMovie(movieID)
                    }
                    .setNegativeButton(resources.getString(R.string.cancel)){ dialog: DialogInterface, _ ->
                        dialog.dismiss()
                    }
                    .show()
            },
            { movieID ->
              findNavController().navigate(FavoriteMovieFragmentDirections.actionSavedMovieFragmentToMovieDetail(movieID))
            }
        )
        binding.rvSavedMovie.apply {
            adapter = favoriteMovieAdapter
            layoutManager = LinearLayoutManager(
                this@FavoriteMovieFragment.context,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }
}