package com.soft.eventos.data.repository

import com.soft.eventos.data.api.ApiService

class EventsRepository (private val apiService: ApiService){
    suspend fun getAllEvents() = apiService.getEvents()
}