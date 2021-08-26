package com.soft.eventos.ui.detailsEvents

import android.util.Log
import androidx.lifecycle.*
import com.soft.eventos.R
import com.soft.eventos.data.model.CheckingEvents
import com.soft.eventos.data.model.Events
import com.soft.eventos.data.repository.EventsRepository
import com.soft.eventos.utils.Resources
import kotlinx.coroutines.Dispatchers

class DetailsEventsViewModel(private val eventsRepository: EventsRepository) : ViewModel() {

    private val _events = MutableLiveData<Events>()
    val events: LiveData<Events> = _events

    private val _stateEventData = MutableLiveData<CheckingState>()
    val stateEventData: LiveData<CheckingState>
        get() = _stateEventData

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
        get() = _messageEventData

    fun sendChecking(name: String, email: String) = liveData(Dispatchers.IO) {
        emit(Resources.loading(data = null))
        try {
            events.value?.let { wtf ->
                val _check = CheckingEvents(wtf.id.toInt(), name, email)
                emit(Resources.success(data = eventsRepository.postChecking(_check)))
                _stateEventData.value = CheckingState.Checking
                _messageEventData.value = R.string.checking_successfully
            }
        } catch (e: Exception) {
            _messageEventData.value = R.string.checking_error
            Log.e(TAG, e.toString())
            emit(Resources.error(data = null, message = e.message ?: "Erro ao enviar comentário"))
        }
    }

    sealed class CheckingState {
        object Checking : CheckingState()
    }

    companion object {
        private val TAG = DetailsEventsViewModel::class.java.simpleName
    }
}