package com.example.composefusion.ui.common_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composefusion.utils.StateController
import kotlinx.coroutines.launch


@Composable
fun LoadingDialog() {
    val isLoadingState by StateController.isLoadingDialogEventFlow.collectAsStateWithLifecycle(false)
    val coroutineScope = rememberCoroutineScope()
    if (isLoadingState) {
        Dialog(
            onDismissRequest = {
                //  dismissOnClickOutside = true that why i add code other wise no need it
                coroutineScope.launch {
                    StateController.sendLoadingDialogEvent(false)
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = true // false
            )
        ) {
            Surface(
                modifier = Modifier
                    .size(100.dp),
                shape = RoundedCornerShape(8.dp),
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 30.dp, vertical =  16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }

}