package ru.avm.movieexersice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.avm.movieexersice.service.MovieService

class MovieListFragment: Fragment()  {

    private var selectedIndex: Int = -1
    var onDetailsListener: ((Long) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recycler = view.findViewById<RecyclerView>(R.id.movieRecycler)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val movies = ArrayList(MovieService.getMovies())

        val adapter = MovieAdapter(layoutInflater, movies, onDetailsListener)

        recycler.adapter = adapter
        recycler.layoutManager = layoutManager

        recycler.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if ((recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() == movies.size - 1) {
                    val start = movies.size - 1
                    val bunch = MovieService.loadBunch()
                    movies.addAll(MovieService.loadBunch())

                    recyclerView.post {
                        recyclerView.adapter?.notifyItemRangeInserted(start, bunch.count())
                    }
                }
            }
        })
    }

    companion object {
        val TAG = MovieListFragment::class.java.simpleName
    }
}