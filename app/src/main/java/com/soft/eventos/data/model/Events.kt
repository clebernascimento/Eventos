package com.soft.eventos.data.model

import java.io.Serializable
import java.util.*


data class Events (
    var id:Long,
    var title: String,
    var date: Long,
    var image: String,
    var description: String,
    var longitude: String,
    var latitude: String,
    var price: String
) : Serializable