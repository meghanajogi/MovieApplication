package com.example.movieapplication.data.models.api


class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getMovies(apikey:String,language:String,page:Int)=apiService.getMovies(apikey,language,page)

    override suspend fun getMovieById( movieId: Int,apikey: String)=apiService.getMovieById(movieId,apikey,)
}