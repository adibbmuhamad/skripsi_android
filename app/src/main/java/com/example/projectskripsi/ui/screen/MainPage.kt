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
import com.example.projectskripsi.data.model.Student
import java.text.SimpleDateFormat
import java.util.Locale

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Divider

@Composable
fun MainPage(
    navController: NavController,
    viewModel: AnnouncementViewModel = viewModel(),
    studentViewModel: StudentViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    // Mengamati perubahan pada daftar pengumuman
    val announcements by remember { derivedStateOf { viewModel.announcements } }
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    // Mengamati perubahan pada daftar siswa
    val students by studentViewModel.students.collectAsState()

    // Memanggil getAnnouncements dan fetchStudents saat MainPage dimuat
    LaunchedEffect(Unit) {
        viewModel.getAnnouncements()
        studentViewModel.fetchStudents()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Menambahkan scroll vertikal
            .background(Color(0xFFF9FAFB)) // Set background color
            .padding(16.dp)
    ) {
        // Kartu untuk menampilkan total jumlah siswa
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), // Menambahkan elevasi
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)) // Set background color to white
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(), // Pastikan Row mengisi lebar penuh
                    verticalAlignment = Alignment.CenterVertically, // Menyelaraskan elemen secara vertikal
                    horizontalArrangement = Arrangement.Start // Mengatur elemen dari kiri
                ) {
                    Icon(
                        imageVector = Icons.Outlined.School, // Ganti dengan ikon yang sesuai
                        contentDescription = "Pengumuman",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Menambahkan sedikit ruang antara ikon dan teks
                    Text(
                        text = "Total Siswa",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF09090B)
                        )
                    )
                }
                Text(
                    text = "${students.size}", // Menampilkan jumlah siswa
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF09090B)
                    ),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        // Display Announcements Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), // Menambahkan elevasi
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)) // Set background color to white
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
                    Column {
                        announcements
                            .sortedByDescending { it.publishedAt } // Urutkan berdasarkan tanggal terbaru
                            .take(5)
                            .forEach { announcement -> // Batasi hanya 5 pengumuman
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
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), // Menambahkan elevasi
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)) // Set background color to white
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

                // Tampilkan daftar siswa jika ada
                if (students.isEmpty()) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Tidak ada siswa tersedia", color = Color.Gray)
                    }
                } else {
                    Column {
                        students
                            .sortedByDescending { it.createdAt }
                            .take(5)
                            .forEach { student -> // Urutkan dan batasi hanya 5 siswa
                                SimpleStudentItem(student = student, navController = navController)
                            }
                    }
                }
            }
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

