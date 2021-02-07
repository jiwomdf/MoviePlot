package com.katilijiwo.movieplot.ui.savedmovie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.katilijiwo.movieplot.R
import com.katilijiwo.movieplot.data.local.entity.Movie
import com.katilijiwo.movieplot.databinding.ListSavedMovieBinding

class SavedMovieAdapter(
    private val deleteClick: (movieId: Int) -> Unit,
    private val navigateClick: (movieId: Int) -> Unit,
): RecyclerView.Adapter<SavedMovieAdapter.SavedMovieViewHolder>() {

    private val diffCallback = object: DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var listData : List<Movie>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedMovieViewHolder {
        val binding = DataBindingUtil.inflate<ListSavedMovieBinding>(
            LayoutInflater.from(parent.context), R.layout.list_saved_movie, parent, false
        )
        return SavedMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedMovieViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner class SavedMovieViewHolder(private val binding: ListSavedMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.tvTitle.text = movie.title
            binding.tvTagLine.text = movie.tagline
            binding.tvImbdIdValue.text = movie.imdbId
            binding.tvVoteIdValue.text = movie.vote
            Glide.with(binding.ivMovie.context)
                .load(movie.img)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(binding.ivMovie)
            binding.fabDeleteMovie.setOnClickListener {
                deleteClick.invoke(movie.id)
            }
            binding.clSavedMovie.setOnClickListener {
                navigateClick.invoke(movie.id)
            }
        }
    }
}