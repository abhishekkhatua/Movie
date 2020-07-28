package com.example.movieapp.data

data class MovieDetails(
	val metascore: String? = null,
	val boxOffice: String? = null,
	val website: String? = null,
	val imdbRating: String? = null,
	val imdbVotes: String? = null,
	val Ratings: List<RatingsItem?>? = null,
	val Runtime: String? = null,
	val language: String? = null,
	val Rated: String? = null,
	val Production: String? = null,
	val Released: String? = null,
	val imdbID: String? = null,
	val Plot: String? = null,
	val Director: String? = null,
	val Title: String? = null,
	val actors: String? = null,
	val response: String? = null,
	val Type: String? = null,
	val awards: String? = null,
	val dVD: String? = null,
	val Year: String? = null,
	val Poster: String? = null,
	val country: String? = null,
	val Genre: String? = null,
	val writer: String? = null
)

data class RatingsItem(
	val value: String? = null,
	val source: String? = null
)

