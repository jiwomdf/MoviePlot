package com.katilijiwo.movieplot.ui.moviedetail.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.katilijiwo.movieplot.R
import com.katilijiwo.movieplot.data.remote.json.reviewmoviejson.Result
import com.katilijiwo.movieplot.databinding.ListMovieCommentBinding
import com.katilijiwo.movieplot.util.Constant.IMAGE_URL_PREFIX_200
import java.text.SimpleDateFormat
import java.util.*

class CommentAdapter: PagingDataAdapter<Result, CommentAdapter.CommentViewHolder>(USERS_COMPARATOR) {

    companion object {
        private val USERS_COMPARATOR = object : DiffUtil.ItemCallback<Result>(){
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ListMovieCommentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(it)
        }
    }

    inner class CommentViewHolder(private val binding: ListMovieCommentBinding): RecyclerView.ViewHolder(
        binding.root
    ){
        fun bind(data: Result){
            Glide.with(binding.ivAvatar.context)
                .load("$IMAGE_URL_PREFIX_200${data.authorDetails.avatarPath}")
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(binding.ivAvatar)
            binding.tvAuthor.text = "Writing by ${data.author}"
            binding.tvPostDate.text = formatDate(data.updatedAt)
            binding.tvComment.text = data.content
        }

        private fun formatDate(strDate: String): String {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            return formatter.format(parser.parse(strDate)!!)
        }
    }

}