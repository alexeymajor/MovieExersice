package ru.avm.movieexersice.service

import de.svenjacobs.loremipsum.LoremIpsum
import ru.avm.movieexersice.R
import ru.avm.movieexersice.domain.Movie
import java.util.concurrent.atomic.AtomicLong
import kotlin.random.Random

object MovieService {

    private val sequence = AtomicLong()
    val favorites = HashSet<Long>()

    private val movies = HashMap<Long, Movie>().apply {
        getBunch().forEach { movie: Movie -> this[movie.id] = movie }
    }

    private fun getBunch() = listOf(
        Movie(sequence.getAndIncrement(),"Назад в будущее", LoremIpsum().getParagraphs(Random.nextInt(1,4)), R.drawable.btf1),
        Movie(sequence.getAndIncrement(),"Назад в будущее 2", LoremIpsum().getParagraphs(Random.nextInt(1,4)), R.drawable.btf2),
        Movie(sequence.getAndIncrement(),"Назад в будущее 3", LoremIpsum().getParagraphs(Random.nextInt(1,4)), R.drawable.btf3),
        Movie(sequence.getAndIncrement(),"Патруль времени", LoremIpsum().getParagraphs(Random.nextInt(1,4)), R.drawable.predestination)
    )

    fun loadBunch() = getBunch().apply {
        this.forEach { movie: Movie -> movies[movie.id] = movie }
    }

    fun getMovies() = movies.values.toList()

    fun getMovie(id: Long) = movies[id]

    fun like(id: Long) {
        favorites.add(id)
    }

    fun unlike(id: Long) {
        favorites.remove(id)
    }

    fun isFavorite(id: Long) = favorites.contains(id)

}