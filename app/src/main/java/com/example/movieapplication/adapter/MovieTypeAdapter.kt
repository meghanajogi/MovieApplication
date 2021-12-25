package com.example.movieapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapplication.R
import com.example.movieapplication.data.models.Genres
import com.example.movieapplication.data.models.Results
import kotlinx.android.synthetic.main.genres_item_layout.view.*
import kotlinx.android.synthetic.main.movie_item_layout.view.*

class MovieTypeAdapter : RecyclerView.Adapter<MovieTypeAdapter.DataViewHolder>() {
    private var genresList= emptyList<Genres>()

    class DataViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        fun bind(genres: Genres){
            itemView.genresTextView.text=genres.name

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.genres_item_layout, parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(genresList[position])
    }

    override fun getItemCount(): Int {
      return  genresList.size
    }
    fun setData(list:List<Genres>){
        genresList=list
        notifyDataSetChanged()
    }


}