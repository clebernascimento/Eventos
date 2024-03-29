package com.soft.eventos.data.repository

import com.soft.eventos.data.api.ApiService
import com.soft.eventos.data.model.CheckingEvents
import com.soft.eventos.ui.detailsEvents.DetailsEventsViewModel
import java.util.zip.CheckedInputStream

class EventsRepository(private val apiService: ApiService) {
    suspend fun getAllEvents() = apiService.getEvents()
    suspend fun postChecking(checkingEvents: CheckingEvents) =
            apiService.postChecking(checkingEvents)
}