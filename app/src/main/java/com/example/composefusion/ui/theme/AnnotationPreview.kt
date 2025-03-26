package com.example.composefusion.ui.theme

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true,
    device = "spec:parent=pixel_5,orientation=landscape"
)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true)
@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true,
    device = "spec:parent=pixel_5,orientation=landscape"
)
annotation class DefaultPreview