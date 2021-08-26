package com.soft.eventos.data.model

data class CheckingEvents(
    var eventsId: Int,
    var name: String,
    var email: String
)

data class ChekingResponse(
    val id: Int,
    val name: String,
    val email: String
)