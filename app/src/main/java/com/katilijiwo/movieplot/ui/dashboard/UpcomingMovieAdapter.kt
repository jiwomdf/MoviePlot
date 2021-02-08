package com.katilijiwo.movieplot.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.katilijiwo.movieplot.R
import com.katilijiwo.movieplot.data.remote.json.upcomingmoviejson.Result
import com.katilijiwo.movieplot.databinding.ListUpcomingMovieBinding
import com.katilijiwo.movieplot.util.Constant.IMAGE_URL_PREFIX_200

class UpcomingMovieAdapter(
    private val movieClick: (movieId: Int) -> Unit
) : RecyclerView.Adapter<UpcomingMovieAdapter.UpComingMovieViewHolder>() {

    private val diffCallback = object: DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Result, newItem: Result) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var listData : List<Result>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpComingMovieViewHolder {
        val binding: ListUpcomingMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_upcoming_movie, parent, false
        )
        return UpComingMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpComingMovieViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner class UpComingMovieViewHolder(
        private val binding: ListUpcomingMovieBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(movie: Result){
            Glide.with(binding.ivMovie.context)
                .load("$IMAGE_URL_PREFIX_200${movie.posterPath}")
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(binding.ivMovie)
            binding.tvTitle.text = movie.title

            binding.llMovie.setOnClickListener {
                movieClick.invoke(movie.id)
            }
        }
    }
}