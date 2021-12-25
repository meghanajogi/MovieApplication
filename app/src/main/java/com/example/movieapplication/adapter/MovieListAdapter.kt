package com.example.movieapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapplication.R
import com.example.movieapplication.data.models.Results
import kotlinx.android.synthetic.main.movie_item_layout.view.*

class MovieListAdapter(var dataListener: DataListener): RecyclerView.Adapter<MovieListAdapter.DataViewHolder>() {

    private var movieList= emptyList<Results>()


class DataViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
    fun bind(movie: Results, dataListener: DataListener){
        itemView.movieName.text=movie.title
       itemView.itemLayout.setOnClickListener {
           dataListener.itemClicked(movie.id)
       }
        Glide.with(itemView.imageViewAvatar.context)
            .load(movie.poster_path)
            .error(R.drawable.movie_time)
            .fitCenter()
            .into(itemView.imageViewAvatar)


    }

}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):DataViewHolder=
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_item_layout, parent,
                false
            )
        )


    override fun getItemCount(): Int {

        return movieList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {


       holder.bind(movieList[position],dataListener)
    }

    fun setData(list:List<Results>){
        movieList=list
        notifyDataSetChanged()
    }
    interface DataListener{
        fun itemClicked(movieId:Int)
    }






}