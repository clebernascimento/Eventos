package com.soft.eventos.ui.detailsEvents

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.soft.eventos.data.model.CheckinEvents
import com.soft.eventos.data.model.Events
import com.soft.eventos.data.repository.EventsRepository
import com.soft.eventos.utils.Resources
import kotlinx.coroutines.Dispatchers

class DetailsEventsViewModel(private val eventsRepository: EventsRepository) : ViewModel() {

    private val _Events = MutableLiveData<Events>()
    val events: LiveData<Events> = _Events

    fun sendChecking(checking: CheckinEvents) = liveData(Dispatchers.IO) {
        emit(Resources.loading(data = null))
        try {
            events.value?.let {
                emit(Resources.success(data = eventsRepository.postChecking(checking)))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("error", e.message.orEmpty())
            emit(Resources.error(data = null, message = e.message ?: "Erro ao enviar checking"))
        }
    }
}