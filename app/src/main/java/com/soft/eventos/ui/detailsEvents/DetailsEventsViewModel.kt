package com.soft.eventos.ui.detailsEvents

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.soft.eventos.R
import com.soft.eventos.data.model.CheckingEvents
import com.soft.eventos.data.repository.EventsRepository
import com.soft.eventos.utils.Resources.Companion.error
import com.soft.eventos.utils.Resources.Companion.loading
import com.soft.eventos.utils.Resources.Companion.success
import kotlinx.coroutines.Dispatchers

class DetailsEventsViewModel(private val eventsRepository: EventsRepository) : ViewModel() {
    fun sendChecking(eventsName: String, eventsEmail: String) = liveData(Dispatchers.IO) {
        emit(loading(data = null))
        try {
            val _events = CheckingEvents(eventsName, eventsEmail)
            emit(success(data = eventsRepository.postChecking(_events)))
        } catch (exception: Exception) {
            exception.printStackTrace()
            Log.e("error", exception.message.orEmpty())
            emit(error(data = null, message = (exception.message ?: R.string.checking_error) as String))
        }
    }
}