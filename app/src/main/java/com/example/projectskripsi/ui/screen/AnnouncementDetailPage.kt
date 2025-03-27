package com.example.projectskripsi.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AnnouncementDetailPage(
    navController: NavController,
    viewModel: AnnouncementDetailViewModel,
    announcementId: Int,
    modifier: Modifier = Modifier
) {
    val announcement = remember { viewModel.selectedAnnouncement }
    val isLoading = remember { viewModel.isLoading }
    val errorMessage = remember { viewModel.errorMessage }

    LaunchedEffect(announcementId) {
        viewModel.getAnnouncementDetail(announcementId)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp, top = 32.dp)
    ) {
        if (isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (errorMessage.value.isNotEmpty()) {
            Text(text = errorMessage.value, color = MaterialTheme.colorScheme.error)
        } else {
            announcement.value?.let {
                Log.d("AnnouncementDetailPage", "Displaying announcement: $it")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = it.title ?: "No Title",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = it.description ?: "No Content",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Published at: ${it.publishedAt ?: "Unknown"}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            } ?: run {
                Log.d("AnnouncementDetailPage", "Announcement not found")
                Text(text = "Pengumuman tidak ditemukan", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AnnouncementDetailPagePreview() {
    // Preview untuk menampilkan tampilan composable di editor
    AnnouncementDetailPage(
        navController = NavController(LocalContext.current),
        viewModel = AnnouncementDetailViewModel(),
        announcementId = 1
    )
}