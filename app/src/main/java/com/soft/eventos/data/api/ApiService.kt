package com.soft.eventos.data.api

import com.soft.eventos.data.model.Events
import retrofit2.http.GET

interface ApiService {
    @GET("/api/events")
    suspend fun getEvents(): List<Events>
}