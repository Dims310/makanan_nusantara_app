package com.dicoding.mymakanannusantara

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    val name: String,
    val desc: String,
    val photo: String,
    val price: Int
) : Parcelable
