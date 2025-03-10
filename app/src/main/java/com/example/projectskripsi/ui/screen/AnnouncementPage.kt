package com.example.projectskripsi.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectskripsi.data.model.Announcement

@Composable
fun AnnouncementPage(navController: NavController, viewModel: AnnouncementViewModel, modifier: Modifier = Modifier) {
    // Ambil data pengumuman
    val announcements = viewModel.announcements
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value

    // Ambil data pengumuman saat pertama kali dibuka
    LaunchedEffect(true) {
        viewModel.getAnnouncements()
    }

    Column(modifier = modifier.padding(16.dp)) {
        // Jika loading tampilkan CircularProgressIndicator
        if (isLoading) {
//            CircularProgressIndicator(modifier = Modifier)
        }
        // Jika ada error tampilkan pesan error
        else if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }
        // Jika data ada, tampilkan daftar pengumuman
        else {
            LazyColumn {
                items(announcements) { announcement ->
                    AnnouncementItem(announcement = announcement, navController = navController)
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}

@Composable
fun AnnouncementItem(announcement: Announcement, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Navigasi ke halaman detail pengumuman
                navController.navigate("announcement_detail/${announcement.id}")
            }
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = announcement.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = announcement.body,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AnnouncementPagePreview() {
    // Gunakan Preview untuk melihat tampilannya
    AnnouncementPage(navController = NavController(LocalContext.current), viewModel = AnnouncementViewModel())
}