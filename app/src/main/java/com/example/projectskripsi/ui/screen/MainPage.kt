package com.example.projectskripsi.ui.screen

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projectskripsi.data.model.Announcement
import com.example.projectskripsi.data.model.Student
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MainPage(
    navController: NavController,
    viewModel: AnnouncementViewModel = viewModel(),
    studentViewModel: StudentViewModel = viewModel(),
    classroomViewModel: ClassroomViewModel = viewModel(),
    achievementViewModel: AchievementViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    // Mengamati perubahan pada daftar pengumuman
    val announcements by remember { derivedStateOf { viewModel.announcements } }
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    // Mengamati perubahan pada daftar siswa
    val students by studentViewModel.students.collectAsState()

    // Mengamati perubahan pada daftar kelas
    val classrooms by classroomViewModel.classrooms.collectAsState()

    // Mengamati perubahan pada daftar pencapaian
    val achievements by achievementViewModel.achievements.collectAsState()

    // Memanggil getAnnouncements, fetchStudents, getClassrooms, dan getAchievements saat MainPage dimuat
    LaunchedEffect(Unit) {
        viewModel.getAnnouncements()
        studentViewModel.fetchStudents()
        classroomViewModel.getClassrooms()
        achievementViewModel.getAchievements()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF9FAFB))
            .padding(16.dp)
    ) {
        // Grid layout 2x2 untuk kartu statistik
        Column(modifier = Modifier.fillMaxWidth()) {
            // Baris pertama dengan 2 kartu
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Kartu Total Siswa
                StatisticCard(
                    icon = Icons.Outlined.School,
                    title = "Total Siswa",
                    value = "${students.size}",
                    modifier = Modifier.weight(1f),
                    onClick = {}
                )

                // Kartu Total Kelas
                StatisticCard(
                    icon = Icons.Outlined.MeetingRoom,
                    title = "Kelas",
                    value = "${classrooms.size}",
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate("classroom_list_page") }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Baris kedua dengan 2 kartu
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Kartu Total Pencapaian
                StatisticCard(
                    icon = Icons.Outlined.Star,
                    title = "Pencapaian",
                    value = "${achievements.size}",
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate("achievement_page") }
                )

                // Kartu Total Pengumuman
                StatisticCard(
                    icon = Icons.Outlined.CalendarMonth,
                    title = "Pengumuman",
                    value = "${announcements.size}",
                    modifier = Modifier.weight(1f),
                    onClick = {}
                )
            }
        }

        // Display Announcements Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(16.dp))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
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
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
                } else {
                    Column {
                        announcements
                            .sortedByDescending { it.publishedAt }
                            .take(5)
                            .forEach { announcement ->
                                SimpleAnnouncementItem(announcement = announcement)
                            }
                    }
                }
            }
        }

        // Display Students Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(16.dp))
            ) {
                Column(Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                    Text(
                        text = "Siswa Terbaru",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF09090B)
                        ),
                    )
                    Text(
                        text = "Daftar siswa yang baru bergabung",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF909096)
                        )
                    )
                }

                if (students.isEmpty()) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Tidak ada siswa tersedia", color = Color.Gray)
                    }
                } else {
                    Column {
                        students
                            .sortedByDescending { it.createdAt }
                            .take(5)
                            .forEach { student ->
                                SimpleStudentItem(student = student, navController = navController)
                            }
                    }
                }
            }
        }
    }
}

@Composable
fun StatisticCard(
    icon: ImageVector,
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF09090B)
                    )
                )
            }
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF09090B)
                ),
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
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
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
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
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF909096)
                    )
                )
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                thickness = 1.dp,
                color = Color(0xFFD9D9D9)
            )
        }
    }
}

@Composable
fun SimpleStudentItem(student: Student, navController: NavController) {
    // Format tanggal menjadi "dd/MM/yyyy"
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val formattedDate = dateFormat.format(student.createdAt)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF) // Set background color to white
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp), // Consistent padding inside card
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Menambahkan InitialsAvatar
            InitialsAvatar(name = student.name, modifier = Modifier.padding(end = 16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = student.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF09090B) // Set text color to #09090B
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF909096)
                        )
                    )
                }
                Text(
                    text = "Kelas ${student.classRoomName}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF909096)
                    )
                )
            }
        }
    }
}

