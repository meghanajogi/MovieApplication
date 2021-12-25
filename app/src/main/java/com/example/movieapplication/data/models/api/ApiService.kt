package com.example.movieapplication.data.models.api

import com.example.movieapplication.data.models.GetAllMovies
import com.example.movieapplication.data.models.GetMovieDetailsById
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("top_rated")
    suspend fun getMovies(@Query("api_key") apiKey:String,@Query("language") language:String,@Query("page") page:Int):GetAllMovies

    @GET("{movie_id}")
    suspend fun getMovieById(@Path("movie_id") movieId:Int,@Query("api_key") apiKey:String):GetMovieDetailsById

}