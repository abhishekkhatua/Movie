package com.example.movieapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieViewModel : ViewModel() {

    var movieListViewData = MutableLiveData<List<SearchMovieEntity.SearchItem>>()
    var progressBarStatus = MutableLiveData<Boolean>().apply { value= false }
    var message  = MutableLiveData<String>()



    fun getMoviewSearchData(query: String) {
        progressBarStatus.value = true
        var movieRep = MovieRepository()
        movieRep.getMoviewList(query, object : MovieRepository.onSearchData {
            override fun onSucess(searchItemList: List<SearchMovieEntity.SearchItem>) {
                movieListViewData.value = searchItemList
                progressBarStatus.value = false

            }

            override fun onFailure(error: String) {
                if (error == "Movie list is Empty") {
                   progressBarStatus.value = false
                    message.value = "Movie list is Empty"
                } else {
                    progressBarStatus.value = false
                    message.value = "Please try again"
                }

            }


        })


    }

}