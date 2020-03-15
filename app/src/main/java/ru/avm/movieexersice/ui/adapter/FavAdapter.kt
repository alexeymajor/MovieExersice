package ru.avm.movieexersice.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.avm.movieexersice.R
import ru.avm.movieexersice.service.MovieService
import ru.avm.movieexersice.ui.viewholder.MovieViewHolder

class FavAdapter(
    private val inflater: LayoutInflater,
    private val onDetailsListener: ((Long) -> Unit)?
) : RecyclerView.Adapter<MovieViewHolder>() {

    private var favorites = MovieService.getMovies().filter { movie -> MovieService.favorites.contains(movie.id) }.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            inflater.inflate(
                R.layout.item_movie,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = favorites.size

    fun deleteItem(position: Int) : Long {
        val movieId = favorites[position].id
        MovieService.unlike(favorites[position].id)
        favorites.removeAt(position)
        this.notifyItemRemoved(position)
        return movieId
    }

    fun revertItem(position: Int, movieId: Long) {
        MovieService.like(movieId)
        MovieService.getMovie(movieId)?.let {
            favorites.add(position, it)
            this.notifyItemInserted(position)
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(
            favorites[position].title, favorites[position].resource, true,
            View.OnClickListener { onDetailsListener?.invoke(favorites[position].id) },
            null
        )
    }
}