package com.example.movieapplication.data.models.api

import com.example.movieapplication.data.models.GetAllMovies
import com.example.movieapplication.data.models.GetMovieDetailsById

interface ApiHelper {

    suspend fun getMovies(apikey:String,language:String,page:Int):GetAllMovies

    suspend fun getMovieById(movieId:Int,apikey:String): GetMovieDetailsById
}