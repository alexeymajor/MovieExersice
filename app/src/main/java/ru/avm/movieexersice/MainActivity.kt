package ru.avm.movieexersice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import ru.avm.movieexersice.dto.UserInput

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = MovieListFragment()
        fragment.onDetailsListener = this::showDetails

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment, MovieListFragment.TAG)
            .commit()
    }

    fun showDetails(movieId: Long) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, MovieDetailsFragment.newInstance(movieId), MovieDetailsFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    fun showFavorites() {

        val fragment = FavoritesListFragment()
        fragment.onDetailsListener = this::showDetails

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment, FavoritesListFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        QuitDialog(this).let {
            it.setOnCancelListener{super.onBackPressed()}
            it.show()
        }
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
                showFavorites()
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
