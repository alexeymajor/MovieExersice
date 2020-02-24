package ru.avm.movieexersice

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.avm.movieexersice.MainActivity.Companion.RESULT_CODE
import ru.avm.movieexersice.dto.UserInput
import ru.avm.movieexersice.service.MovieService

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var userInput: UserInput

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        intent.getIntExtra("index", -1).let {index ->
            MovieService.movies[index].let {movie ->
                findViewById<TextView>(R.id.description)?.text = movie.description
                findViewById<ImageView>(R.id.cover)?.setImageResource(movie.resource)
                findViewById<TextView>(R.id.title)?.text = movie.title
            }
        }

        savedInstanceState?.let {
            userInput = it.get(SAVED_INPUT) as UserInput
        } ?: kotlin.run{
            userInput = UserInput()
        }

        setResult(Activity.RESULT_OK, Intent().apply { putExtra(RESULT_CODE, userInput) })

        findViewById<CheckBox>(R.id.likeBox)?.let {checkBox ->
            checkBox.isChecked = userInput.like
            checkBox.setOnClickListener {userInput.like = checkBox.isChecked}
        }

        findViewById<EditText>(R.id.commentBox)?.let {edit ->
            edit.setText(userInput.comment)
            edit.onChange { userInput.comment = it }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(SAVED_INPUT, userInput)
    }

    companion object {
        const val SAVED_INPUT = "userInput"
    }
}

fun EditText.onChange(consumer: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) { consumer(s.toString()) }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}