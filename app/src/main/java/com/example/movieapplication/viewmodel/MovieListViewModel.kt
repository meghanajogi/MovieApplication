package com.example.movieapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplication.data.models.GetAllMovies
import com.example.movieapplication.data.models.GetMovieDetailsById
import com.example.movieapplication.data.models.api.ApiHelper
import com.example.movieapplication.utils.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieListViewModel(private val apiHelper: ApiHelper):ViewModel() {
    var id:Int?=null
    var apiKey="2db4277841272b4851386883db0b4e76"
    var language="en-US"
    var page=0
    private val movies=MutableLiveData<Resource<GetAllMovies>>()
    private val movie=MutableLiveData<Resource<GetMovieDetailsById>>()


    fun fetchMovies() {
        viewModelScope.launch {
            movies.postValue(Resource.loading(null))
            try {
            val moviesFromApi=apiHelper.getMovies(apiKey,language,page)
           movies.postValue(Resource.success(moviesFromApi))
            }
            catch (e:Exception)
            {
                movies.postValue(Resource.error(e.toString(),null))
        } }
    }

  fun getMovieDetails(){
        viewModelScope.launch {
            movie.postValue(Resource.loading(null))
            try {
                val movieFromApi= id?.let { apiHelper.getMovieById( it,apiKey) }
                movie.postValue(Resource.success(movieFromApi))
            }
            catch (e:Exception)
            {
                movie.postValue(Resource.error(e.toString(),null))
            }
        }
    }

    fun getMovies(): LiveData<Resource<GetAllMovies>> {
        return movies
    }
    fun getMovieById(): LiveData<Resource<GetMovieDetailsById>> {
        return movie
    }
}