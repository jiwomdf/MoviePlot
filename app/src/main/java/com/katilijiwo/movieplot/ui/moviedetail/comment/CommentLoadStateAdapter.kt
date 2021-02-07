package com.katilijiwo.movieplot.ui.moviedetail.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.katilijiwo.movieplot.databinding.LayoutLoadsStateCommentBinding

class CommentLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<CommentLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = LayoutLoadsStateCommentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoadStateViewHolder(binding)
    }

    inner class LoadStateViewHolder(private val binding: LayoutLoadsStateCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(loadState: LoadState) {
                val progress = binding.loadStateProgress
                val btnRetry = binding.loadStateRetry
                val txtErrorMessage = binding.loadStateErrorMessage

                btnRetry.isVisible = loadState !is LoadState.Loading
                txtErrorMessage.isVisible = loadState !is LoadState.Loading
                progress.isVisible = loadState is LoadState.Loading

                if (loadState is LoadState.Error){
                    txtErrorMessage.text = loadState.error.localizedMessage
                }

                btnRetry.setOnClickListener {
                    retry.invoke()
                }
            }
        }
}