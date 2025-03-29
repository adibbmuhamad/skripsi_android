package com.example.projectskripsi.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectskripsi.data.model.Announcement
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.projectskripsi.ui.component.CustomAppBar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementPage(navController: NavController, viewModel: AnnouncementViewModel, modifier: Modifier = Modifier) {
    val announcements = viewModel.announcements
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value

    LaunchedEffect(true) {
        viewModel.getAnnouncements()
    }

    var selectedTabIndex by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    val tabTitles = listOf("Semua", "Penting", "Info", "Acara")
    // State untuk SwipeRefresh
    val isRefreshing = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Pengumuman",
                onBackClick = { navController.popBackStack() }
            )
        },
        containerColor = Color(0xFFF9FAFB)
    ) { innerPadding ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing.value),
            onRefresh = {
                isRefreshing.value = true
                viewModel.getAnnouncements() // Memanggil ulang data pengumuman
                isRefreshing.value = false // Setel kembali ke false setelah selesai
            }
        ) {
        Column(modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
            .background(Color(0xFFF9FAFB))) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            } else {
                // Search UI
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = {
                        Text(
                            "Cari pengumuman...",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                            )
                        )
                    },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon", tint = Color(0xFF909096)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color(0xFFF2F5F9),
                        cursorColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = Color(0xFF909096)
                    )
                )

                // Tab Row for categories
                val scrollState = rememberScrollState()
                Row(
                    modifier = Modifier
                        .horizontalScroll(scrollState)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 4.dp)
                                .background(
                                    color = if (selectedTabIndex == index) MaterialTheme.colorScheme.primary else Color(
                                        0xFFF2F5F9
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .clickable { selectedTabIndex = index }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = title,
                                color = if (selectedTabIndex == index) Color.White else Color.Black,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                )
                            )
                        }
                    }
                }

                // Filter announcements based on selected category and search query
                val filteredAnnouncements = announcements.filter {
                    (selectedTabIndex == 0 || it.category.equals(getOriginalCategoryName(tabTitles[selectedTabIndex]), ignoreCase = true)) &&
                            it.title.contains(searchQuery, ignoreCase = true)
                }.sortedByDescending { it.publishedAt }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF9FAFB))
                ) {
                    items(filteredAnnouncements) { announcement ->
                        AnnouncementItem(announcement = announcement, navController = navController)
                    }
                }
            }
        }}
    }
}

fun getOriginalCategoryName(translatedCategory: String): String {
    return when (translatedCategory.lowercase()) {
        "penting" -> "important"
        "info" -> "info"
        "acara" -> "event"
        else -> translatedCategory
    }
}

@Composable
fun AnnouncementItem(announcement: Announcement, navController: NavController) {
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
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = announcement.description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF666666)
                ),
                textAlign = TextAlign.Justify
            )
        }
    }
}

fun translateCategory(category: String): String {
    return when (category.lowercase()) {
        "important" -> "Penting"
        "info" -> "Info"
        "event" -> "Acara"
        else -> category
    }
}

fun formatPublishedDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: dateString
    } catch (e: Exception) {
        dateString
    }
}

@Composable
fun getCategoryBackgroundColor(category: String): Color {
    return when (category.lowercase()) {
        "important" -> Color(0xFFFFE0E0)
        "info" -> Color(0xFFE0F7FA)
        "event" -> Color(0xFFE8F5E9)
        else -> Color(0xFFE0E0E0)
    }
}

@Composable
fun getCategoryTextColor(category: String): Color {
    return when (category.lowercase()) {
        "important" -> Color(0xFFD32F2F)
        "info" -> Color(0xFF00796B)
        "event" -> Color(0xFF388E3C)
        else -> Color(0xFF666666)
    }
}

@Preview
@Composable
private fun AnnoucementPagePreview() {
    AnnouncementPage(navController = rememberNavController(), viewModel = AnnouncementViewModel())


}