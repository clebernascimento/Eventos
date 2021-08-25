package com.soft.eventos.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Events (
    var id:Long,
    var title: String,
    var date: Long,
    var image: String,
    var description: String,
    var longitude: String,
    var latitude: String,
    var price: String
) : Parcelable