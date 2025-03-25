package com.example.composefusion.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext


object StateController {
    private val _isLoadingDialogEvent = Channel<Boolean>()
    val isLoadingDialogEventFlow = _isLoadingDialogEvent.receiveAsFlow()

    suspend fun sendLoadingDialogEvent(event: Boolean) {
        _isLoadingDialogEvent.send(event)
    }

    private val _messageEvent  = Channel<UiText>()
    val messageEventFlow = _messageEvent.receiveAsFlow()

    suspend fun sendToastMsgEvent(event: UiText) {
        _messageEvent.send(event)
    }

    @Composable
    fun <T> ObserveAsEvents(
        flow: Flow<T>,
        key1: Any? = null,
        key2: Any? = null,
        onEvent: (T) -> Unit
    ) {
        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(lifecycleOwner.lifecycle, key1, key2, flow) {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                withContext(Dispatchers.Main.immediate) {
                    flow.collect(onEvent)
                }
            }
        }
    }
}