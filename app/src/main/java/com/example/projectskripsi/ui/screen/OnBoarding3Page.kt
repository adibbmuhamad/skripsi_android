package com.example.projectskripsi.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.ui.Alignment

@Composable
fun OnBoarding3Page(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icon for Dashboard
        Icon(
            imageVector = Icons.Default.Dashboard,
            contentDescription = "Dashboard",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Title for Dashboard and Rekapitulasi Data
        Text(
            text = "Semua Informasi di Ujung Jari Anda",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Description for Dashboard and Rekapitulasi Data
        Text(
            text = "Dashboard kami dirancang untuk memberikan Anda ringkasan aktivitas harian anak Anda dengan tampilan yang mudah dipahami. Dari absensi hingga prestasi, semua data tersedia dalam satu tempat. Butuh laporan lengkap? Unduh rekap data kapan saja, di mana saja. Dengan sistem kami, Anda tidak hanya memantau, tetapi juga berpartisipasi aktif dalam perjalanan pendidikan anak Anda.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Button to finish onboarding and navigate to the main screen
        Button(onClick = { navController.navigate("login_screen") }) {
            Text("Mulai sekarang dan jadilah bagian dari perubahan!")
        }
    }
}