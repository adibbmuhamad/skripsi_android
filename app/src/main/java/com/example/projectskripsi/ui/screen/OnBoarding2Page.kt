package com.example.projectskripsi.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.ui.Alignment

@Composable
fun OnBoarding2Page(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icon for Prestasi
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Prestasi",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Title for Prestasi and Kesehatan
        Text(
            text = "Rayakan Prestasi dan Pantau Kesehatan Anak Anda",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Description for Prestasi and Kesehatan
        Text(
            text = "Setiap prestasi anak Anda adalah kebanggaan kita bersama. Dengan sistem kami, Anda akan mendapatkan laporan prestasi mereka secara langsung. Tidak hanya itu, kesehatan anak Anda juga menjadi prioritas kami. Akses laporan kesehatan dan tindakan medis yang diambil, semua dalam satu aplikasi. Dengan informasi yang lengkap, Anda dapat mendukung perkembangan anak Anda dengan lebih baik.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Button to navigate to the next onboarding page
        Button(onClick = { navController.navigate("onboarding3_screen") }) {
            Text("Temukan bagaimana kami memudahkan Anda!")
        }
    }
}