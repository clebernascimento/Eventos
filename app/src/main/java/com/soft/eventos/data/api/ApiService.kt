package com.soft.eventos.data.api

import com.soft.eventos.data.model.CheckingEvents
import com.soft.eventos.data.model.Events
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("/api/events")
    suspend fun getEvents(): List<Events>

    @POST("/checkin")
    suspend fun postChecking(@Body checking: CheckingEvents)
}