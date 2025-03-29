package com.example.projectskripsi.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectskripsi.R
import com.example.projectskripsi.data.model.Announcement


import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainPage(navController: NavController, viewModel: AnnouncementViewModel = viewModel(), modifier: Modifier = Modifier) {
    // Mengamati perubahan pada daftar pengumuman
    val announcements by remember { derivedStateOf { viewModel.announcements } }
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    // Memanggil getAnnouncements saat MainPage dimuat
    LaunchedEffect(Unit) {
        viewModel.getAnnouncements()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB)) // Set background color
            .padding(16.dp)
    ) {
        // Display Feature Buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // First Column for first 3 buttons
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FeatureButton(
                    iconRes = R.drawable.ic_trophy,
                    label = "Achievements",
                    onClick = {
                        navController.navigate("achievement_page")
                    }
                )

                FeatureButton(
                    iconRes = R.drawable.ic_megaphone,
                    label = "Announcements",
                    onClick = {
                        navController.navigate("announcement_page")
                    }
                )

                FeatureButton(
                    iconRes = R.drawable.ic_user,
                    label = "Attendances",
                    onClick = {
                        navController.navigate("attendance_page")
                    }
                )
            }

            Spacer(modifier = Modifier.width(24.dp))

            // Second Column for last 3 buttons
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FeatureButton(
                    iconRes = R.drawable.ic_heart,
                    label = "Health Reports",
                    onClick = {
                        navController.navigate("health_report_page")
                    }
                )

                FeatureButton(
                    iconRes = R.drawable.ic_academic,
                    label = "Students",
                    onClick = {
                        navController.navigate("student_list")
                    }
                )

                FeatureButton(
                    iconRes = R.drawable.ic_x_circle,
                    label = "Violations",
                    onClick = {
                        navController.navigate("violation_page")
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Display Announcements Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.CalendarMonth, // Ganti dengan ikon yang sesuai
                contentDescription = "Pengumuman",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Pengumuman",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF09090B)
                ),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Tampilkan Semua",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.clickable { navController.navigate("announcement_page") }
            )
        }

        if (isLoading) {
            // Tampilkan indikator loading jika sedang memuat
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (errorMessage.isNotEmpty()) {
            // Tampilkan pesan error jika ada
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        } else {
            // Tampilkan pengumuman jika ada
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Pastikan LazyColumn mengisi ruang yang tersisa
            ) {
                items(announcements) { announcement ->
                    SimpleAnnouncementItem(announcement = announcement)
                }
            }
        }
    }
}

@Composable
private fun FeatureButton(
    iconRes: Int,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            IconButton(
                onClick = onClick,
                modifier = Modifier.size(72.dp)
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null, // Decorative image
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun SimpleAnnouncementItem(announcement: Announcement) {
    val formattedDate = formatPublishedDate(announcement.publishedAt)
    val translatedCategory = translateCategory(announcement.category)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = announcement.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF09090B)
                    ),
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Box(
                    modifier = Modifier
                        .background(
                            getCategoryBackgroundColor(announcement.category),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = translatedCategory,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = getCategoryTextColor(announcement.category),
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "Published Date",
                    tint = Color(0xFF999999),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF999999)
                    )
                )
            }
        }
    }
}

