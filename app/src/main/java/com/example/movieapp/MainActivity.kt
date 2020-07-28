package com.example.movieapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.databinding.MovieAdapterLayoutBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var viewModel: MovieViewModel
    lateinit var adapter: MovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpViewModel()


    }


    fun setUpViewModel() {
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(MovieViewModel::class.java)

        setUpUi()
        viewModel.movieListViewData.observe(this, movieListObserver)
        viewModel.progressBarStatus.observe(this, progressBarStatus)
        viewModel.message.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        })
    }

    fun setUpUi() {
        adapter = MovieAdapter(
            viewModel.movieListViewData.value ?: emptyList<SearchMovieEntity.SearchItem>(),
            this ,openMovieDetailsActivityClickListner = {
                var intent  =  Intent(this,MovieDetailsActivity::class.java)
                intent.putExtra("id",it)
                startActivity(intent)
            }
        )
        activityMainBinding.recyclerview.layoutManager = LinearLayoutManager(this)
        activityMainBinding.recyclerview.adapter = adapter
        activityMainBinding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    Log.i("callApi", "CallApi")
                    viewModel.getMoviewSearchData(query)
                } else {
                    Toast.makeText(this@MainActivity, "No Match found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    //observers
    val movieListObserver = Observer<List<SearchMovieEntity.SearchItem>> {
        adapter.update(it)

    }
    val progressBarStatus = Observer<Boolean> {
        if (it) {
            activityMainBinding.progressbar.visibility = View.VISIBLE
        } else {
            activityMainBinding.progressbar.visibility = View.GONE

        }
    }
}

class MovieAdapter(var movieArrayList: List<SearchMovieEntity.SearchItem>, var context: Context,  val openMovieDetailsActivityClickListner: (id:String) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
   inner class MovieViewHolder(var view: MovieAdapterLayoutBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(searchItem: SearchMovieEntity.SearchItem, context: Context) {

            view.movieName.text = searchItem.Title
            view.yearOfRelease.text = "Year of release ${searchItem.Year}"
            if (searchItem.Poster.equals("N/A")){
                view.moviePoster.setImageResource(R.drawable.ic_baseline_image_24)
            }else {
                Glide.with(context).load(searchItem.Poster).into(view.moviePoster)
            }
            view.movieClick.setOnClickListener {
                openMovieDetailsActivityClickListner(searchItem.imdbID!!)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val dataBinding =
            MovieAdapterLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(dataBinding)
    }

    fun update(data: List<SearchMovieEntity.SearchItem>) {

        movieArrayList = data
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {

        return movieArrayList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        holder.bind(movieArrayList[position], context)
    }


}