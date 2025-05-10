package com.example.projectskripsi.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = PurpleGrey40,
    tertiary = Pink40
    // Tambahkan warna lainnya jika diperlukan
)

@Composable
fun ProjectSkripsiTheme(
    darkTheme: Boolean = false, // Set to false to disable dark mode
    dynamicColor: Boolean = false, // Disable dynamic color here
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            dynamicLightColorScheme(context) // Will not be used as dynamicColor is false
        }
        else -> LightColorScheme // Always use light color scheme
    }

    val view = LocalView.current
    SideEffect {
        val window = (view.context as? Activity)?.window
        window?.statusBarColor = colorScheme.primary.toArgb() // Set status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val insetsController = ViewCompat.getWindowInsetsController(view)
            insetsController?.isAppearanceLightStatusBars = true // Set light status bar icons
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Gunakan Typography yang telah Anda definisikan
        content = content
    )
}
