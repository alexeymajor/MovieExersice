package ru.avm.movieexersice.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.avm.movieexersice.R
import ru.avm.movieexersice.ui.adapter.FavAdapter

class MovieFavoritesListFragment: Fragment() {

    var onDetailsListener: ((Long) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = FavAdapter(layoutInflater, onDetailsListener)
        val recycler = view.findViewById<RecyclerView>(R.id.favoritesRecycler)
        recycler.adapter = adapter
        recycler.layoutManager = layoutManager

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedId = adapter.deleteItem(position)

                Snackbar.make(view, getString(R.string.snackRemoved), Snackbar.LENGTH_INDEFINITE).apply {
                    this.setAction(getString(R.string.snackUndo)) { adapter.revertItem(position, deletedId) }
                    this.show()
                }
            }

        }).attachToRecyclerView(recycler)
    }

    companion object {
        val TAG = MovieFavoritesListFragment::class.java.simpleName
    }
}