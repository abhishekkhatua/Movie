package com.example.movieapp.data

data class SearchMovieData(
	val response: String? = null,
	val totalResults: String? = null,
	val search: List<SearchItem?>? = null
) {
	data class SearchItem(
		val type: String? = null,
		val year: String? = null,
		val imdbID: String? = null,
		val poster: String? = null,
		val title: String? = null
	)
}



