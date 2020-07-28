package com.example.movieapp

import com.example.movieapp.data.MovieDetails
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object ApiClient {

    private val BASE_API = "http://www.omdbapi.com/"


    private var seviceApiInterface: ServiceApiInterface? = null

    fun build(): ServiceApiInterface {

        var builder = Retrofit.Builder().baseUrl(BASE_API)
            .addConverterFactory(GsonConverterFactory.create())


        var httpClient = OkHttpClient.Builder().addInterceptor(inteceptor())

        var retrofit =
            builder.client(httpClient.build()).build().create(ServiceApiInterface::class.java)


        return retrofit
    }

    private fun inteceptor(): HttpLoggingInterceptor {

        var httpIntecptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return httpIntecptor
    }

    interface ServiceApiInterface {

        @GET("http://www.omdbapi.com/?apikey=71231b3e&type=movie&plot=full")
        fun searchMovie(@Query("s")query:String): Call<SearchMovieEntity>

        @GET("http://www.omdbapi.com/?apikey=71231b3e")
        fun getMovieDetails(@Query("i")movieId: String):Call<MovieDetails>

    }

}