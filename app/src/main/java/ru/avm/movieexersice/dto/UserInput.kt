package ru.avm.movieexersice.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInput(
    var like: Boolean = false,
    var comment: String? = null
): Parcelable