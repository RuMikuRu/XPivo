package com.example.xpivo.core.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel(@ApplicationContext context: Context) : ViewModel() {
    private val _event = MutableSharedFlow<SingleEvent<String>>()
    val event: SharedFlow<SingleEvent<String>> = _event.asSharedFlow()

    protected fun <T> stateFlowWithLce(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend () -> T,
        retry: (() -> Unit)? = null
    ): StateFlow<Lce<T>> {
        val state = MutableStateFlow<Lce<T>>(Lce.Loading)

        val retry: () -> Unit = {
            viewModelScope.launch(dispatcher) {
                state.emit(Lce.Loading)
                try {
                    val result = block()
                    state.emit(Lce.Content(result))
                } catch (e: Exception) {
                    state.emit(Lce.Error(e, retry = retry))
                    sendError(e)
                }
            }
        }

        retry()

        return state
    }

    private val _unauthorizedEvent = MutableStateFlow<Boolean?>(null)
    val unauthorizedEvent = _unauthorizedEvent.asStateFlow()

    protected fun launchSafely(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        onError: (Throwable) -> Unit = { sendError(it) },
        block: suspend () -> Unit
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                block()
            } catch (e: Exception) {
                if (e.isUnauthorized()) {
                    Log.d("BaseViewModel", "launchSafely: ${e.printStackTrace()}")
                    _unauthorizedEvent.update { true }
                } else {
                    onError(e)
                }
            }
        }
    }
    fun onUnauthorizedEventHandled() {
        _unauthorizedEvent.value = null
    }


    protected fun sendError(e: Throwable) {
        viewModelScope.launch {
            _event.emit(SingleEvent(e.message ?: "Неизвестная ошибка"))
            _unauthorizedEvent.update { true }
            if (e.isUnauthorized()) {
                Log.d("BaseViewModel", "launchSafely: ${e.printStackTrace()}")
                _unauthorizedEvent.update { true }
            }
        }
    }
}

fun Throwable.isUnauthorized(): Boolean {
    return this is retrofit2.HttpException && this.code() == 401
}