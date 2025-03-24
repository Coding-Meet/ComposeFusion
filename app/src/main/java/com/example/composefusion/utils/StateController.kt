package com.example.composefusion.utils

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow


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
}