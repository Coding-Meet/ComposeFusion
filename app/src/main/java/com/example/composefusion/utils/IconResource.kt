package com.example.composefusion.utils

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource

sealed interface IconResource {
    data class ImageVector(val imageVector: androidx.compose.ui.graphics.vector.ImageVector) :
        IconResource

    data class DrawableResource(@DrawableRes val resID: Int) : IconResource

    @Composable
    fun asPainterResource(): Painter {
        return when (this) {
            is DrawableResource -> painterResource(id = resID)
            is ImageVector -> rememberVectorPainter(image = imageVector)
        }
    }

}