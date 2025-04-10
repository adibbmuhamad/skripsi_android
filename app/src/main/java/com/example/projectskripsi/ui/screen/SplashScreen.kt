package com.example.projectskripsi.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import com.example.projectskripsi.R

@Composable
fun SplashScreen(navController: NavController, modifier: Modifier = Modifier) {
    // Menggunakan LaunchedEffect untuk menunda navigasi
    LaunchedEffect(Unit) {
        delay(3000) // Menunda selama 3 detik
        navController.navigate("onboarding1_screen") {
            // Menghapus splash screen dari back stack
            popUpTo("splash_screen") { inclusive = true }
        }
    }

    // Konten visual dari splash screen
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Ganti dengan logo Anda
                contentDescription = "Logo",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Ortu Connect",
                color = Color.White,
                fontSize = 24.sp
            )
        }
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen(navController = NavHostController(LocalContext.current))
}