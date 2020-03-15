package ru.avm.movieexersice

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import ru.avm.movieexersice.domain.Movie
import ru.avm.movieexersice.service.MovieService

class MovieAdapter(private val activity: Activity, private val movies: List<Movie>) : RecyclerView.Adapter<MovieViewHolder>() {

    private val inflater = LayoutInflater.from(activity)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position].title, movies[position].resource, MovieService.isFavorite(movies[position].id),
            View.OnClickListener {
                Intent(activity, MovieDetailsActivity::class.java).apply {
                putExtra("index", position)
                activity.startActivityForResult(this, MainActivity.REQUEST_CODE)
                } },
            CompoundButton.OnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    MovieService.like(movies[position].id)
                } else {
                    MovieService.unlike(movies[position].id)
                }
            }
        )
    }
}