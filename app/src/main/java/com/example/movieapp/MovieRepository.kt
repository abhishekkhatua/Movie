package com.example.movieapp

import android.util.Log
import com.example.movieapp.data.MovieDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository {


    fun getMoviewList(query:String,onsearchData:onSearchData){

         ApiClient.build().searchMovie(query).enqueue(object :Callback<SearchMovieEntity>{
            override fun onFailure(call: Call<SearchMovieEntity>?, t: Throwable?) {
                onsearchData.onFailure("error")
            }

            override fun onResponse(
                call: Call<SearchMovieEntity>?,
                response: Response<SearchMovieEntity>
            ) {
                if (response.isSuccessful) {
                    Log.i("Responsess",response.body().toString())
                    if (response.body().Response.equals("False")){
                        onsearchData.onFailure("Movie list is Empty")

                    }else {
                        onsearchData.onSucess(response.body().Search)
                    }
                }else{
                    onsearchData.onFailure("error")
                }
            }


        })
    }



    fun getMovieDetailsById(movieId:String,onMovieDetails: onMovieDetails){

        ApiClient.build().getMovieDetails(movieId).enqueue(object :Callback<MovieDetails>{
            override fun onFailure(call: Call<MovieDetails>?, t: Throwable?) {
                onMovieDetails.onFailure()
            }

            override fun onResponse(call: Call<MovieDetails>?, response: Response<MovieDetails>) {

                if (response.isSuccessful){
                    onMovieDetails.onSucess(response.body())

                }else{
                    onMovieDetails.onFailure()

                }
            }


        })






    }

    interface onMovieDetails{
        fun onSucess(movieDetails:MovieDetails)
        fun onFailure()


    }


    interface onSearchData{
        fun onSucess(searchItemList:List<SearchMovieEntity.SearchItem>)
        fun onFailure(errorMessage:String)


    }
}