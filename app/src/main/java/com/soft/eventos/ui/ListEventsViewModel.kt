package com.soft.eventos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.soft.eventos.data.repository.EventsRepository
import com.soft.eventos.utils.Resources
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ListEventsViewModel(private val eventsRepository: EventsRepository) : ViewModel() {
    fun getEvents() = liveData(Dispatchers.IO) {
        emit(Resources.loading(data = null))
        try {
            emit(Resources.success(data = eventsRepository.getAllEvents()))
        } catch (exception: Exception) {
            emit(
                Resources.error(
                    data = null,
                    message = exception.message ?: "Erro ao listar eventos"
                )
            )
        }
    }
}