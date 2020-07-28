package com.example.movieapp

data class SearchMovieEntity(
	val Response: String? = null,
	val totalResults: String? = null,
	val Search: List<SearchItem>
) {
	data class SearchItem(
		val Type: String? = null,
		val Year: String? = null,
		val imdbID: String? = null,
		val Poster: String? = null,
		val Title: String? = null
	)
}


