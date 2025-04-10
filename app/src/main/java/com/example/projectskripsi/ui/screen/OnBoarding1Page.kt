package com.example.projectskripsi.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.Alignment

@Composable
fun OnBoarding1Page(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icon for Absensi
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Absensi",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Title for Absensi
        Text(
            text = "Pantau Kehadiran dan Kepatuhan Anak Anda",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Description for Absensi
        Text(
            text = "Selamat datang di era baru pelaporan sekolah! Dengan sistem absensi kami, Anda dapat memantau kehadiran anak Anda secara real-time. Dapatkan notifikasi instan jika mereka tidak hadir, sehingga Anda selalu tahu keberadaan mereka. Selain itu, setiap pelanggaran aturan yang dilakukan akan langsung dilaporkan kepada Anda. Transparansi dan komunikasi yang lebih baik untuk memastikan anak Anda tetap berada di jalur yang benar.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Button to navigate to the next onboarding page
        Button(onClick = { navController.navigate("onboarding2_screen") }) {
            Text("Lanjutkan untuk melihat lebih banyak fitur!")
        }
    }
}