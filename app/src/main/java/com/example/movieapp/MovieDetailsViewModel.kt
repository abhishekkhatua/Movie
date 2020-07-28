package com.example.movieapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.MovieDetails

class MovieDetailsViewModel :ViewModel(){

    var movieDetailsList = MutableLiveData<MovieDetails>()

    fun getMovieDetails(movieId:String){
        var movieRep = MovieRepository()
        movieRep.getMovieDetailsById(movieId,object : MovieRepository.onMovieDetails{
            override fun onSucess(movieDetails: MovieDetails) {
                movieDetailsList.value = movieDetails
            }

            override fun onFailure() {

            }


        })

    }



}