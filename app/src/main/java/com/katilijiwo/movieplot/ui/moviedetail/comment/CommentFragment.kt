package com.katilijiwo.movieplot.ui.moviedetail.comment

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.katilijiwo.movieplot.R
import com.katilijiwo.movieplot.base.BaseFragment
import com.katilijiwo.movieplot.data.CommentPagingSource
import com.katilijiwo.movieplot.data.remote.json.reviewmoviejson.Result
import com.katilijiwo.movieplot.databinding.FragmentCommentBinding
import com.katilijiwo.movieplot.ui.moviedetail.MovieDetailStateAdapter
import com.katilijiwo.movieplot.ui.moviedetail.MovieDetailViewModel
import com.katilijiwo.movieplot.util.AbsentLiveData
import com.katilijiwo.movieplot.util.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentFragment: BaseFragment<FragmentCommentBinding>(
    R.layout.fragment_comment
) {

    private val viewModel: MovieDetailViewModel by viewModel()
    lateinit var commentAdapter: CommentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments?.getInt(MovieDetailStateAdapter.MOVIE_ID) == null){
            showError(isFinish = true, isCancelable = false)
        } else {
            viewModel.searchReview(arguments?.getInt(MovieDetailStateAdapter.MOVIE_ID)!!)
        }

        binding.llLoading.bringToFront()
        initRecyclerView()
        setObserver()
    }

    private fun setObserver() {
        super.setListener()

        viewModel.reviews.observe(viewLifecycleOwner, {
            commentAdapter.submitData(lifecycle, it)
        })

        commentAdapter.addLoadStateListener { combinedLoadStates  ->
            if(combinedLoadStates.source.refresh is LoadState.NotLoading &&
                combinedLoadStates.append.endOfPaginationReached &&
                commentAdapter.itemCount < 1){
                setComponentVisibility(DATA_NOT_FOUND)
            } else {
                setComponentVisibility(DATA_FOUND)
            }

            when(combinedLoadStates.source.refresh){
                is LoadState.Loading -> {
                    setProgressBarLoading(true)
                }
                is LoadState.NotLoading -> {
                    setProgressBarLoading(false)
                }
                is LoadState.Error -> {
                    setProgressBarLoading(false)
                    setComponentVisibility(DATA_NOT_FOUND)
                }
            }
        }
    }

    private fun setComponentVisibility(status: Int){
        when(status){
            DATA_FOUND -> {
                binding.tvCommentNotFound.visibility = View.GONE
                binding.rvComments.visibility = View.VISIBLE
            }
            DATA_NOT_FOUND -> {
                binding.tvCommentNotFound.visibility = View.VISIBLE
                binding.rvComments.visibility = View.GONE
            }
        }
    }

    private fun initRecyclerView(){
        commentAdapter = CommentAdapter()
        binding.rvComments.apply {
            adapter = commentAdapter.withLoadStateHeaderAndFooter(
                header = CommentLoadStateAdapter {
                    commentAdapter.retry()
                },
                footer = CommentLoadStateAdapter {
                    commentAdapter.retry()
                }
            )
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setProgressBarLoading(isLoading: Boolean){
        if(isLoading){
            binding.llLoading.visibility = View.VISIBLE
            activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else {
            binding.llLoading.visibility = View.GONE
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

}