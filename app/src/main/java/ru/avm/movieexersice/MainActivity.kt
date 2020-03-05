package ru.avm.movieexersice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import ru.avm.movieexersice.dto.UserInput
import ru.avm.movieexersice.service.MovieService

class MainActivity : AppCompatActivity() {

    private var selectedIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState?.let {
            selectedIndex = it.getInt(SAVED_SELECTED_INDEX, -1)
        }
    }

    override fun onBackPressed() {
        QuitDialog(this).let {
            it.setOnCancelListener{super.onBackPressed()}
            it.show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SAVED_SELECTED_INDEX, selectedIndex)
    }

    override fun onStart() {
        super.onStart()

        for ((index, group) in GROUPS.withIndex()) {
            if (index > MovieService.movies.size - 1) {
                break
            }

            findViewById<ImageView>(resources.getIdentifier("${group}MovieImage", "id", packageName))
                ?.setImageResource(MovieService.movies[index].resource)

            findViewById<TextView>(resources.getIdentifier("${group}Title", "id", packageName))?.let {
                it.text = MovieService.movies[index].title
                if (selectedIndex == index) {
                    it.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.colorAccent))
                } else {
                    it.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.colorText))
                }
            }

            findViewById<Button>(resources.getIdentifier("${group}Button", "id", packageName))
                ?.setOnClickListener {
                    selectedIndex = index
                    Intent(this, MovieDetailsActivity::class.java).apply {
                        putExtra("index", index)
                        startActivityForResult(this, REQUEST_CODE)
                } }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_invite -> {
                Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_INTENT, "invite friend")
                    resolveActivity(packageManager)?.let { startActivity(Intent.createChooser(this, "invite")) }
                }
                true
            }
            R.id.action_theme -> {
                AppCompatDelegate.setDefaultNightMode(MY_THEME_MAGIC_INT - AppCompatDelegate.getDefaultNightMode())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.let {
            (it.getParcelableExtra(RESULT_CODE) as? UserInput).apply {
                Log.d(TAG, this.toString())
            }
        }
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName
        const val REQUEST_CODE = 123
        const val SAVED_SELECTED_INDEX = "ssi"
        const val RESULT_CODE = "userInput"
        const val MY_THEME_MAGIC_INT = 3
        val GROUPS = arrayOf("first", "second", "third", "fourth")
    }
}
