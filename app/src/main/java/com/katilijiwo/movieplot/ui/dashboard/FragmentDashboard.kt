package com.katilijiwo.movieplot.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.katilijiwo.movieplot.R
import com.katilijiwo.movieplot.base.BaseFragment
import com.katilijiwo.movieplot.databinding.FragmentDashboardBinding
import com.katilijiwo.movieplot.util.Constant.SELECTED_PAGE
import com.katilijiwo.movieplot.util.MovieEvent
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class FragmentDashboard : BaseFragment<FragmentDashboardBinding>(
    R.layout.fragment_dashboard
), View.OnClickListener{

    private var popularIdx = 0
    private var errorCounter = 0
    private val viewModel: DashboardViewModel by viewModel()
    private val args: FragmentDashboardArgs by navArgs()
    lateinit var popularMovieAdapter: PopularMovieAdapter
    lateinit var upcomingMovieAdapter: UpcomingMovieAdapter
    private var coroutineSwipe: Job? = null

    override fun onStart() {
        super.onStart()
        setupSlider()
    }

    override fun onPause() {
        super.onPause()
        coroutineSwipe?.cancel()
        errorCounter = 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpComponent()
        setupRecyclerView()
        viewModel.fetchPopularMovie(SELECTED_PAGE)
        viewModel.fetchUpComingMovie(SELECTED_PAGE)
    }

    private fun setUpComponent() {
        binding.llLoading.bringToFront()
        binding.tvName.text = args.name
        val timeOfDay: Int = Calendar.getInstance(Locale.getDefault()).get(Calendar.HOUR_OF_DAY)
        binding.tvGreeting.text = when (timeOfDay) {
            in 0..11 -> getString(R.string.text_good_morning)
            in 12..15 -> getString(R.string.text_good_afternoon)
            in 16..20 -> getString(R.string.text_good_evening)
            in 21..23 -> getString(R.string.text_good_night)
            else -> getString(R.string.text_good_day)
        }
    }

    private fun setupSlider() {
        coroutineSwipe = lifecycleScope.launch(Dispatchers.Default) {
            while (true){
                delay(3000)
                if(popularIdx >= popularMovieAdapter.listData.size)
                    popularIdx = 0
                withContext(Dispatchers.Main){
                    binding.rvPopularMovie.smoothScrollToPosition(popularIdx)
                    Log.d("Coroutine", popularIdx.toString())
                }
                popularIdx++
            }
        }
    }

    override fun setListener() {
        super.setListener()

        binding.ivSaveImage.setOnClickListener(this)

        viewModel.popularMovies.observe(viewLifecycleOwner, { popularMovie ->
            when (popularMovie) {
                is MovieEvent.Success -> {
                    setComponentVisibility(DATA_FOUND, "")
                    popularMovieAdapter.listData = popularMovie.data
                    popularMovieAdapter.notifyDataSetChanged()
                }
                is MovieEvent.Error -> {
                    errorCounter++
                    setComponentVisibility(DATA_NOT_FOUND, popularMovie.message)
                }
                is MovieEvent.Loading -> {/*NO-OP*/
                }
            }
        })

        viewModel.upcomingMovies.observe(viewLifecycleOwner, { upComingMovie ->
            when (upComingMovie) {
                is MovieEvent.Success -> {
                    setComponentVisibility(DATA_FOUND, "")
                    upcomingMovieAdapter.listData = upComingMovie.data
                    upcomingMovieAdapter.notifyDataSetChanged()
                }
                is MovieEvent.Error -> {
                    errorCounter++
                    setComponentVisibility(DATA_NOT_FOUND, upComingMovie.message)
                }
                is MovieEvent.Loading -> {/*NO-OP*/
                }
            }
        })

        viewModel.isFetchingAllDataComplete.observe(viewLifecycleOwner, {
            setProgressBarLoading(it)
        })

        binding.rvPopularMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, dx: Int) {
                super.onScrollStateChanged(recyclerView, dx)
                if (dx === RecyclerView.SCROLL_STATE_IDLE) {
                    val position: Int = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    popularIdx = position
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_save_image -> {
                findNavController().navigate(FragmentDashboardDirections.actionFragmentDashboardToSavedMovieFragment())
            }
        }
    }

    private fun setComponentVisibility(status: Int, message: String){
        when(status){
            DATA_FOUND -> {
                binding.llUpcomingMovie.visibility = View.VISIBLE
                binding.tvPopularMovie.visibility = View.VISIBLE
                binding.tvUpcomingMovieError.visibility = View.GONE
            }
            DATA_NOT_FOUND -> {
                if (errorCounter == 1) {
                    binding.llUpcomingMovie.visibility = View.GONE
                    binding.tvPopularMovie.visibility = View.GONE
                    binding.tvUpcomingMovieError.visibility = View.VISIBLE
                    showError(isCancelable = false, isFinish = false, description = message)
                }
            }
        }
    }

    private fun setProgressBarLoading(counter: Int){
        if(counter >= 2){
            binding.llLoading.visibility = View.GONE
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else {
            binding.llLoading.visibility = View.VISIBLE
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }
    }

    private fun setupRecyclerView(){
        popularMovieAdapter = PopularMovieAdapter{ movieId ->
            findNavController().navigate(
                FragmentDashboardDirections.actionFragmentDashboardToMovieDetail(
                    movieId
                )
            )
        }
        binding.rvPopularMovie.apply {
            adapter = popularMovieAdapter
            layoutManager = LinearLayoutManager(
                this@FragmentDashboard.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            layoutDirection = View.LAYOUT_DIRECTION_LTR
        }

        upcomingMovieAdapter = UpcomingMovieAdapter { movieId ->
            findNavController().navigate(
                FragmentDashboardDirections.actionFragmentDashboardToMovieDetail(
                    movieId
                )
            )
        }
        binding.rvUpcomingMovie.apply {
            adapter = upcomingMovieAdapter
            layoutManager = GridLayoutManager(
                this@FragmentDashboard.context,
                2,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }



}