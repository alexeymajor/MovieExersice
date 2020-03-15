package ru.avm.movieexersice.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import ru.avm.movieexersice.R
import ru.avm.movieexersice.domain.Movie
import ru.avm.movieexersice.service.MovieService
import ru.avm.movieexersice.ui.viewholder.MovieViewHolder

class MovieAdapter(
    private val inflater: LayoutInflater,
    private val movies: List<Movie>,
    private val onDetailsListener: ((Long) -> Unit)?
) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            inflater.inflate(
                R.layout.item_movie,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position].title, movies[position].resource, MovieService.isFavorite(movies[position].id),
            View.OnClickListener { onDetailsListener?.invoke(movies[position].id) },
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