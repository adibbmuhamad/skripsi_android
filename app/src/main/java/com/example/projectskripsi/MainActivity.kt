package com.example.projectskripsi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.projectskripsi.ui.navigation.AppNavigation
import com.example.projectskripsi.ui.theme.ProjectSkripsiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectSkripsiTheme {
                AppNavigation() // Ini menjadi root composable
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainPagePreview() {
    ProjectSkripsiTheme {
        val navController = rememberNavController()
        AppNavigation() // Preview menggunakan AppNavigation lengkap
    }
}

// Preview khusus untuk MainPage (opsional)
@Preview(showBackground = true)
@Composable
private fun SingleMainPagePreview() {
    ProjectSkripsiTheme {
        val navController = rememberNavController()
        com.example.projectskripsi.ui.screen.MainPage(
            navController = navController,
            modifier = Modifier.fillMaxSize()
        )
    }
}