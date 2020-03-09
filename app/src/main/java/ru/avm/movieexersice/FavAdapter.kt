package ru.avm.movieexersice

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import ru.avm.movieexersice.service.MovieService

class FavAdapter(private val activity: Activity) : RecyclerView.Adapter<MovieViewHolder>() {

    private val inflater = LayoutInflater.from(activity)
    private var favorites = MovieService.getMovies().filter { movie -> MovieService.favorites.contains(movie.id) }.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun getItemCount() = favorites.size

    fun deleteItem(position: Int) {
        MovieService.unlike(favorites[position].id)
        favorites.removeAt(position)
        this.notifyItemRemoved(position)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(
            favorites[position].title, favorites[position].resource, true,
            View.OnClickListener {},
            CompoundButton.OnCheckedChangeListener { _, _ -> }
        )
    }
}