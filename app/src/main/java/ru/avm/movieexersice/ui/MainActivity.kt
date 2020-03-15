package ru.avm.movieexersice.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import ru.avm.movieexersice.R
import ru.avm.movieexersice.ui.dialog.QuitDialog
import ru.avm.movieexersice.ui.fragment.MovieDetailsFragment
import ru.avm.movieexersice.ui.fragment.MovieFavoritesListFragment
import ru.avm.movieexersice.ui.fragment.MovieListFragment

class MainActivity : AppCompatActivity() {

    lateinit var drawer: DrawerLayout
    lateinit var fav: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = MovieListFragment()
        fragment.onDetailsListener = this::showDetails

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer, fragment,
                MovieListFragment.TAG
            )
            .commit()

        findViewById<NavigationView>(R.id.nav_view).let {
            fav = it.menu.findItem(R.id.actionFavorite)
            it.setNavigationItemSelectedListener { item -> this.onOptionsItemSelected(item) }
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawerLayout)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_header_desc, R.string.nav_header_desc)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        supportFragmentManager.addOnBackStackChangedListener {
            supportFragmentManager.fragments.last().let {
                fav.isVisible = it.tag != MovieFavoritesListFragment.TAG
                when (it.tag) {
                    MovieFavoritesListFragment.TAG -> {
                        toolbar.visibility = View.VISIBLE
                        toolbar.title = getString(R.string.favorites)

                    }
                    MovieListFragment.TAG -> {
                        toolbar.visibility = View.VISIBLE
                        toolbar.title = getString(R.string.app_name)
                    }
                    else -> toolbar.visibility = View.GONE
                }
           }
        }
    }

    private fun showDetails(movieId: Long) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                MovieDetailsFragment.newInstance(
                    movieId
                ),
                MovieDetailsFragment.TAG
            )
            .addToBackStack(null)
            .commit()
    }

    private fun showFavorites() {

        val fragment = MovieFavoritesListFragment()
        fragment.onDetailsListener = this::showDetails

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer, fragment,
                MovieFavoritesListFragment.TAG
            )
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount != 0) {
            super.onBackPressed()
            return
        }

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
            android.R.id.home -> {
                supportFragmentManager.popBackStack()
                true
            }
            R.id.actionTheme -> {
                drawer.closeDrawers()
                AppCompatDelegate.setDefaultNightMode(MY_THEME_MAGIC_INT - AppCompatDelegate.getDefaultNightMode())
                true
            }
            R.id.actionFavorite -> {
                showFavorites()
                drawer.closeDrawers()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName
//        const val REQUEST_CODE = 123
//        const val SAVED_SELECTED_INDEX = "ssi"
        const val RESULT_CODE = "userInput"
        const val MY_THEME_MAGIC_INT = 3
    }

}
