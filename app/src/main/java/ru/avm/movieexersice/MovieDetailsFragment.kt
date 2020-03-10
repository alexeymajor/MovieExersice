package ru.avm.movieexersice

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.avm.movieexersice.dto.UserInput
import ru.avm.movieexersice.service.MovieService

class MovieDetailsFragment: Fragment() {

    private var userInput: UserInput = UserInput()

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
        arguments?.getLong(MOVIE_ID)?.apply {
            MovieService.getMovie(this)?.apply {
                view.findViewById<TextView>(R.id.description)?.text = this.description
                view.findViewById<ImageView>(R.id.cover)?.setImageResource(this.resource)
                view.findViewById<TextView>(R.id.title)?.text = this.title
            }

            view.findViewById<CheckBox>(R.id.likeBox)?.let { checkBox ->
                checkBox.isChecked = userInput.like
                checkBox.setOnClickListener {userInput.like = checkBox.isChecked}
            }

            view.findViewById<EditText>(R.id.commentBox)?.let {edit ->
                edit.setText(userInput.comment)
                edit.onChange { userInput.comment = it }
            }

        }
    }

    companion object {
        val TAG = MovieDetailsFragment::class.java.simpleName
        const val MOVIE_ID = "MOVIE_ID"
        fun newInstance(movieId: Long): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
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