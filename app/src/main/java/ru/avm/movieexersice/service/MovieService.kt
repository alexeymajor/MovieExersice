package ru.avm.movieexersice.service

import de.svenjacobs.loremipsum.LoremIpsum
import ru.avm.movieexersice.R
import ru.avm.movieexersice.domain.Movie

object MovieService {

    val movies = listOf(
        Movie("Назад в будущее", LoremIpsum().getParagraphs(1), R.drawable.btf1),
        Movie("Назад в будущее 2", LoremIpsum().getParagraphs(2), R.drawable.btf2),
        Movie("Назад в будущее 3", LoremIpsum().getParagraphs(3), R.drawable.btf3),
        Movie("Патруль времени", LoremIpsum().getParagraphs(2), R.drawable.predestination)
    )

    var selected: Int? = null

}