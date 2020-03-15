package ru.avm.movieexersice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        initRecycler()
    }

    private fun initRecycler() {
        val recycler = findViewById<RecyclerView>(R.id.movieItemsRecycler)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val movies = ArrayList(MovieService.getMovies())

        recycler.adapter = MovieAdapter(this, movies)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionInvite -> {
                Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_INTENT, "invite friend")
                    resolveActivity(packageManager)?.let { startActivity(Intent.createChooser(this, "invite")) }
                }
                true
            }
            R.id.actionTheme -> {
                AppCompatDelegate.setDefaultNightMode(MY_THEME_MAGIC_INT - AppCompatDelegate.getDefaultNightMode())
                true
            }
            R.id.actionFavorite -> {
                Intent(this, FavActivity::class.java).apply {
                    startActivity(this)
                }
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
    }
}
