package com.example.composefusion

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.composefusion.ui.common_components.LoadingDialog
import com.example.composefusion.ui.theme.ComposeFusionTheme
import com.example.composefusion.utils.ObserveAsEvents
import com.example.composefusion.utils.StateController
import com.example.composefusion.utils.UiText
import com.example.composefusion.utils.isNetworkAvailable
import com.example.composefusion.utils.showToast
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }
            var isDynamicColor by remember { mutableStateOf(false) }
            ComposeFusionTheme(
                darkTheme = isDarkTheme,
                dynamicColor = isDynamicColor
            ) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ThemeChange(
                            isDarkTheme = isDarkTheme,
                            isDynamicColor = isDynamicColor, onThemeCheckedChange = {
                                isDarkTheme = it
                            }, onDynamicColorCheckedChange = {
                                isDynamicColor = it
                            }
                        )
                        LoadingScreen()
                        ShowToast()
                        NetworkCheck()
                    }
                }
            }
        }
    }
}

@Composable
private fun ThemeChange(
    isDarkTheme: Boolean,
    isDynamicColor: Boolean,
    onThemeCheckedChange: (Boolean) -> Unit,
    onDynamicColorCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Theme: ${if (isDarkTheme) "Dark" else "Light"}")
        Switch(isDarkTheme, onCheckedChange = { onThemeCheckedChange(it) })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Text("Dynamic Color: ${if (isDynamicColor) "On" else "Off"}")
            Switch(isDynamicColor, onCheckedChange = { onDynamicColorCheckedChange(it) })
        }
    }
}

@Composable
private fun LoadingScreen() {
    LoadingDialog()
    val coroutineScope = rememberCoroutineScope()
    Button(onClick = {
        coroutineScope.launch {
            StateController.sendLoadingDialogEvent(true)
        }
    }) {
        Text(text = "Show Loading Dialog")
    }
}

@Composable
private fun ShowToast() {
    val context = LocalContext.current
    ObserveAsEvents(StateController.messageEventFlow) {
        context.showToast(it.asString(context))
    }
    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = {
            coroutineScope.launch {
                StateController.sendToastMsgEvent(UiText.DynamicString("Hello World"))
            }
        }) {
            Text(text = "Show Toast Dynamic String")
        }
        Button(onClick = {
            coroutineScope.launch {
                StateController.sendToastMsgEvent(UiText.StringResource(resId = R.string.app_name))
            }
        }){
            Text(text = "Show Toast String Resource")
        }
    }
}

@Composable
private fun NetworkCheck() {
    val context = LocalContext.current

    Button(onClick = {
        if (context.isNetworkAvailable()) {
            context.showToast("Network is available")
        } else {
            context.showToast("Network is not available")
        }
    }) {
        Text(text = "Check Network")
    }
}