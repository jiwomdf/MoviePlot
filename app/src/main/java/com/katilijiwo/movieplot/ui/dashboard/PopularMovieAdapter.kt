package com.katilijiwo.movieplot.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.katilijiwo.movieplot.R
import com.katilijiwo.movieplot.data.remote.json.popularmoviejson.Result
import com.katilijiwo.movieplot.databinding.ListPopularMovieBinding
import com.katilijiwo.movieplot.util.Constant.IMAGE_URL_PREFIX_200
import com.katilijiwo.movieplot.util.Constant.IMAGE_URL_PREFIX_500

class PopularMovieAdapter(
    private val movieClick: (movieId: Int) -> Unit
): RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder>() {

    private val diffCallback = object: DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Result, newItem: Result) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var listData : List<Result>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        val binding = DataBindingUtil.inflate<ListPopularMovieBinding>(
            LayoutInflater.from(parent.context), R.layout.list_popular_movie, parent, false
        )
        return PopularMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int  = listData.size

    inner class PopularMovieViewHolder(
        private val binding: ListPopularMovieBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie: Result){
            Glide.with(binding.ivPopularMovie.context)
                .load("$IMAGE_URL_PREFIX_500${movie.backdropPath}")
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(binding.ivPopularMovie)
            binding.tvPopularMovieTitle.text = movie.title
            binding.llPopularMovie.setOnClickListener {
                movieClick.invoke(movie.id)
            }
        }
    }
}