package com.katilijiwo.movieplot.ui.moviedetail

import android.graphics.Bitmap
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.katilijiwo.movieplot.data.CommentPagingSource
import com.katilijiwo.movieplot.data.Repository
import com.katilijiwo.movieplot.data.local.entity.Movie
import com.katilijiwo.movieplot.data.remote.json.moviedetail.MovieDetailResponse
import com.katilijiwo.movieplot.data.remote.json.reviewmoviejson.Result
import com.katilijiwo.movieplot.util.AbsentLiveData
import com.katilijiwo.movieplot.util.Constant
import com.katilijiwo.movieplot.util.MovieEvent
import com.katilijiwo.movieplot.util.SingleLiveEvent
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val repository: Repository
): ViewModel() {

    data class MovieDetail(
        val id: Int,
        val title: String,
        val tagline: String,
        val imdbId: String,
        val voteAverage: Double,
        val voteCount: Int,
        val overview: String,
    )

    fun getMovieByID(movieID: Int): LiveData<Movie?> = repository.getMovieByID(movieID)

    private var movieDetail = MutableLiveData<MovieDetail>()
    fun setMovieDetail(movieDetail: MovieDetail){
        this.movieDetail.value = movieDetail
    }
    fun getMovieDetail() : LiveData<MovieDetail> {
        return movieDetail
    }

    private var _movieDetailStatus = SingleLiveEvent<MovieEvent<MovieDetailResponse>>()
    val movieDetailStatus: LiveData<MovieEvent<MovieDetailResponse>> = _movieDetailStatus
    fun fetchMovieDetail(movieID: Int){
        viewModelScope.launch {
            try {
                _movieDetailStatus.postValue(MovieEvent.Loading)
                val response = repository.fetchMovieDetail(movieID)
                if(response != null && isResponseNotNull(response) ){
                    setMovieDetail(MovieDetail(
                        response.id,
                        response.title, response.tagline, response.imdbId,
                        response.voteAverage, response.voteCount, response.overview))
                    _movieDetailStatus.postValue(MovieEvent.Success(response))
                } else {
                    _movieDetailStatus.postValue(MovieEvent.Error())
                }
            } catch (ex: Exception){
                _movieDetailStatus.postValue(MovieEvent.Error(ex.message.toString()))
            }
        }
    }

    private fun isResponseNotNull(response: MovieDetailResponse): Boolean {
        if(response.title == null || response.tagline == null || response.imdbId == null ||
        response.voteAverage == null || response.voteCount == null || response.overview == null || response.posterPath == null){
            return false
        }
        return true
    }

    private val movieID = MutableLiveData<Int>()
    val reviews = Transformations.switchMap(movieID){ newMovieID ->
        if(newMovieID == null){
            AbsentLiveData.create()
        } else {
            getSearchResult(newMovieID)
        }
    }
    fun searchReview(movieID: Int){
        this.movieID.value = movieID
    }

    private fun getSearchResult(movieID: Int): LiveData<PagingData<Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constant.API_PER_PAGE,
                maxSize = Constant.PAGER_MAX_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CommentPagingSource(repository, movieID) }
        ).liveData
    }

    fun insertMovie(bitmap: Bitmap, dateTimeStamp: Long){
        viewModelScope.launch {
            repository.insertMovie(
                Movie(
                    getMovieDetail().value!!.id,
                    bitmap,
                    dateTimeStamp,
                    getMovieDetail().value!!.title,
                    getMovieDetail().value!!.tagline,
                    getMovieDetail().value!!.imdbId,
                    getMovieDetail().value!!.voteAverage.toString() + " / " + getMovieDetail().value!!.voteCount.toString(),
                    getMovieDetail().value!!.overview
                )
            )
        }
    }

    fun deleteMovie(){
        viewModelScope.launch {
            repository.deleteMovie(getMovieDetail().value!!.id)
        }
    }


}