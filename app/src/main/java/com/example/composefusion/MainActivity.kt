package com.example.composefusion

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composefusion.ui.theme.ComposeFusionTheme

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
                    ThemeChange(
                        innerPadding = innerPadding,
                        isDarkTheme = isDarkTheme,
                        isDynamicColor = isDynamicColor, onThemeCheckedChange = {
                            isDarkTheme = it
                        }, onDynamicColorCheckedChange = {
                            isDynamicColor = it
                        }
                    )
                }
            }
        }
    }

    @Composable
    private fun ThemeChange(
        innerPadding: PaddingValues,
        isDarkTheme: Boolean,
        isDynamicColor: Boolean,
        onThemeCheckedChange: (Boolean) -> Unit,
        onDynamicColorCheckedChange: (Boolean) -> Unit
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
    }
}