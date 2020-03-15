package ru.avm.movieexersice.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import ru.avm.movieexersice.R
import ru.avm.movieexersice.service.MovieService

class MovieDetailsFragment: Fragment() {

    private var movieId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        val toolbar: Toolbar = view.findViewById(R.id.detailsToolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        arguments?.getLong(MOVIE_ID)?.apply {
            movieId = this
            MovieService.getMovie(this)?.apply {
                view.findViewById<TextView>(R.id.description)?.text = this.description
                view.findViewById<ImageView>(R.id.cover)?.setImageResource(this.resource)
                toolbar.title = this.title
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_details, menu)

        val checkBox: CheckBox = menu.findItem(R.id.actionFavorite).actionView as CheckBox
        checkBox.setButtonDrawable(R.drawable.fav_selector)
        movieId?.let { id ->
            checkBox.isChecked = MovieService.isFavorite(id)
            checkBox.setOnClickListener {
                if (checkBox.isChecked) {
                    MovieService.like(id)
                } else {
                    MovieService.unlike(id)
                }
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        val TAG = MovieDetailsFragment::class.java.simpleName
        const val MOVIE_ID = "MOVIE_ID"
        fun newInstance(movieId: Long): MovieDetailsFragment {
            val fragment =
                MovieDetailsFragment()
            val bundle = Bundle()
            bundle.putLong(MOVIE_ID, movieId)

            fragment.arguments = bundle

            return fragment
        }
    }
}

fun EditText.onChange(consumer: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) { consumer(s.toString()) }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}