package com.soft.eventos.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Events(
    val id: Long,
    val title: String,
    val date: Long,
    val image: String,
    val description: String,
    val longitude: String,
    val latitude: String,
    val price: String
) : Parcelable