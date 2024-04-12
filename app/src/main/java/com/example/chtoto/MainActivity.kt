package com.example.chtoto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    val imdbBaseUrl = "https://tv-api.com/"
    val retrofit = Retrofit.Builder()
        .baseUrl(imdbBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val imdbService = retrofit.create(IMDbApi::class.java)
    lateinit var queryInput: EditText
    lateinit var searchButton: Button
    lateinit var recyclerView: RecyclerView
    var movies = arrayListOf<Movie>()
    var adapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        queryInput  = findViewById(R.id.queryInput)
        searchButton= findViewById(R.id.searchButton)
        recyclerView= findViewById(R.id.recyclerView)
        adapter.movies = movies
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        searchButton.setOnClickListener{
            if (queryInput.text.isNotEmpty()){
                imdbService.findMovie(queryInput.text.toString()).enqueue(object:Callback<MovieResponse>{
                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        showMessage("Фильм не найден")
                        recyclerView.visibility=View.GONE
                    }
                    override fun onResponse(
                        call: Call<MovieResponse>,
                        response: Response<MovieResponse>
                    ) {
                        if (response.code()==200){
                            recyclerView.visibility=View.VISIBLE
                            movies.clear()
                            if (response.body()?.results?.isNotEmpty()==true){
                                movies.addAll(response.body()?.results!!)
                                adapter.notifyDataSetChanged()
                            }else{
                                showMessage("Фильм не найден")
                                recyclerView.visibility=View.GONE
                            }
                        }else{
                            showMessage("Возникла ошибка")
                        }
                    }
                })
            }
        }
    }
    fun showMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}