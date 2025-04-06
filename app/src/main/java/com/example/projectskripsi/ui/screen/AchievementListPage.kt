package com.example.projectskripsi.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projectskripsi.data.model.AchievementList
import com.example.projectskripsi.ui.component.CustomAppBar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AchievementListPage(
    modifier: Modifier = Modifier,
    viewModel: AchievementViewModel = viewModel(),
    navController: NavController
) {
    val achievements by viewModel.achievements.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val swipeRefreshState = rememberSwipeRefreshState(isLoading)

    LaunchedEffect(Unit) {
        viewModel.getAchievements()
    }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Pencapaian",
                onBackClick = { navController.popBackStack() }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFF9FAFB))
            ) {
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = {
                        viewModel.getAchievements()
                    }
                ) {
                    when {
                        isLoading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                        errorMessage.isNotEmpty() -> {
                            Text(
                                text = errorMessage,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        else -> {
                            // Urutkan daftar berdasarkan tanggal terbaru
                            val sortedAchievements = achievements.sortedByDescending { parseDate(it.updatedAt) }
                            LazyColumn(
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(sortedAchievements) { achievement ->
                                    AchievementItem(achievement = achievement)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

// Fungsi untuk mengonversi string tanggal ke Date
fun parseDate(dateString: String): Date? {
    return try {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        format.parse(dateString)
    } catch (e: Exception) {
        null
    }
}

@Composable
fun AchievementItem(achievement: AchievementList) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* Handle click */ }, // Add click feedback
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        ),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Logo inisial nama
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.small
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = getInitials(achievement.studentName),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = achievement.achievementName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF09090B)
                        )
                    )
                    Text(
                        text = "${achievement.studentName} - ${achievement.classRoom}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF909096)
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .background(
                                color = getCategoryColor(achievement.category),
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = achievement.category,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "NISN: ${achievement.nisn}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF909096)
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = achievement.description,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Light,
                            color = Color(0xFF606060)
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Created: ${achievement.updatedAt}",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Light,
                    color = Color(0xFF909096)
                )
            )
        }
    }
}

@Composable
fun getInitials(name: String): String {
    val words = name.split(" ")
    return if (words.size >= 2) {
        "${words[0].first()}${words[1].first()}".uppercase()
    } else {
        name.take(2).uppercase()
    }
}

@Composable
fun getCategoryColor(category: String): Color {
    return when (category) {
        "Sains" -> Color(0xFF34C759)
        "Seni" -> Color(0xFF8E24AA)
        "Olahraga" -> Color(0xFF4CAF50)
        "Teknologi" -> Color(0xFF03A9F4)
        "Sastra" -> Color(0xFFFF9800)
        else -> Color(0xFF909096)
    }
}