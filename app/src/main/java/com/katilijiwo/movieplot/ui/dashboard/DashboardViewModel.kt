package com.katilijiwo.movieplot.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katilijiwo.movieplot.data.Repository
import com.katilijiwo.movieplot.util.MovieEvent
import com.katilijiwo.movieplot.util.SingleLiveEvent
import kotlinx.coroutines.launch

typealias PopularMovie = com.katilijiwo.movieplot.data.remote.json.popularmoviejson.Result
typealias UpComingMovie = com.katilijiwo.movieplot.data.remote.json.upcomingmoviejson.Result

class DashboardViewModel(private val repository: Repository): ViewModel() {

    private var _isFetchingAllDataComplete = MutableLiveData(0)
    val isFetchingAllDataComplete: LiveData<Int> = _isFetchingAllDataComplete

    private var _popularMovies = SingleLiveEvent<MovieEvent<List<PopularMovie>>>()
    val popularMovies: LiveData<MovieEvent<List<PopularMovie>>> = _popularMovies
    fun fetchPopularMovie(page: Int){
        viewModelScope.launch {
            try {
                val response = repository.fetPopularMovies(page)
                if(response.results.isNotEmpty()){
                    val highlight = if(response.results.size >= 5) response.results.take(5) else response.results
                    _popularMovies.postValue(MovieEvent.Success(highlight))
                } else {
                    _popularMovies.postValue(MovieEvent.NotFound())
                }
            }
            catch (ex: Exception){
                _popularMovies.postValue(MovieEvent.Error(ex.message.toString()))
            }
            _isFetchingAllDataComplete.value = _isFetchingAllDataComplete.value?.plus(1)
        }
    }

    private var _upComingMovies = SingleLiveEvent<MovieEvent<List<UpComingMovie>>>()
    val upcomingMovies: LiveData<MovieEvent<List<UpComingMovie>>> = _upComingMovies
    fun fetchUpComingMovie(page: Int){
        viewModelScope.launch {
            try {
                val response = repository.fetchUpcomingMovie(page)
                if(response.results.isNotEmpty()){
                    val highlight = if(response.results.size >= 15) response.results.take(15) else response.results
                    _upComingMovies.postValue(MovieEvent.Success(highlight))
                } else {
                    _upComingMovies.postValue(MovieEvent.NotFound())
                }
            } catch (ex: Exception){
                _upComingMovies.postValue(MovieEvent.Error(ex.message.toString()))
            }
            _isFetchingAllDataComplete.value = _isFetchingAllDataComplete.value?.plus(1)
        }
    }


}