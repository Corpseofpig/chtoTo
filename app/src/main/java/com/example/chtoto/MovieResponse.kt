package com.example.chtoto

 data class MovieResponse (
     val searchType: String,
     val expression: String,
     val results: List<Movie>
 )
