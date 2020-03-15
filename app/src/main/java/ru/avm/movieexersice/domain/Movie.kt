package ru.avm.movieexersice.domain

data class Movie (
    val id: Long,
    val title: String,
    val description: String,
    val resource: Int
)