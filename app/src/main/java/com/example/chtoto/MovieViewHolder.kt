package com.example.chtoto

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var coverImageView = itemView.findViewById<ImageView>(R.id.cover)
    var titleTextView = itemView.findViewById<TextView>(R.id.title)
    var descriptionTextView = itemView.findViewById<TextView>(R.id.description)

    fun bind(movie: Movie){
        titleTextView.text = movie.title
        descriptionTextView.text = movie.description
        Glide.with(itemView)
            .load(movie.image)
            .into(coverImageView)
    }
}