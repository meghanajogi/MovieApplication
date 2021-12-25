package com.example.movieapplication.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapplication.data.models.api.ApiHelper
import com.example.movieapplication.fragment.MovieListFragment
import com.example.movieapplication.viewmodel.MovieListViewModel

class ViewModelFactory(private val apiHelper:ApiHelper) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieListViewModel::class.java)){
            return MovieListViewModel(apiHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}