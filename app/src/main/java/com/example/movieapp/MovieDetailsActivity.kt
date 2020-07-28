package com.example.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ActivityMovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity() {
    lateinit var activityMovieDetailsBinding: ActivityMovieDetailsBinding
    lateinit var movieDetailsViewModel:MovieDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMovieDetailsBinding =  DataBindingUtil.setContentView(this,R.layout.activity_movie_details)
        setUpViewModel()
    }

    fun setUpViewModel(){
        var id = intent.getStringExtra("id")
        movieDetailsViewModel  = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(MovieDetailsViewModel::class.java)
        movieDetailsViewModel.getMovieDetails(id)

        movieDetailsViewModel.movieDetailsList.observe(this, Observer {
            if (it.Poster.equals("N/A")) {
                activityMovieDetailsBinding.moviePoster.setImageResource(R.drawable.ic_baseline_image_24)
            }else {
                Glide.with(this).load(it.Poster).into(activityMovieDetailsBinding.moviePoster)
            }
            activityMovieDetailsBinding.movieName.text = it.Title
            activityMovieDetailsBinding.director.text = "By ${it.Director}"
            activityMovieDetailsBinding.type.text  = it.Type
            activityMovieDetailsBinding.details.text = it.Plot
            if (it.imdbRating.equals("N/A")){
                activityMovieDetailsBinding.rating.visibility = View.GONE
            }else {
                activityMovieDetailsBinding.rating.rating = it.imdbRating!!.toFloat()
            }
            activityMovieDetailsBinding.back.setOnClickListener {
                onBackPressed()
            }
        })

    }
}