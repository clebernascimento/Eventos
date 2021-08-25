package com.soft.eventos.data.repository

import com.soft.eventos.data.api.ApiService
import com.soft.eventos.data.model.CheckinEvents

class EventsRepository (private val apiService: ApiService){
    suspend fun getAllEvents() = apiService.getEvents()
    suspend fun postChecking(checkingEvents: CheckinEvents) = apiService.postChecking(checkingEvents)
}