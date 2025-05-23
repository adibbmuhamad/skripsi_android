package com.example.projectskripsi.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectskripsi.data.model.Achievement
import com.example.projectskripsi.data.model.Attendance
import com.example.projectskripsi.data.model.HealthReport
import com.example.projectskripsi.data.model.Violation
import com.example.projectskripsi.ui.component.CustomAppBar
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDetailPage(navController: NavController, viewModel: StudentViewModel, studentId: Int) {
    // State untuk SwipeRefresh
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(studentId) {
        viewModel.fetchStudentDetail(studentId)
    }

    val studentDetailResponse = viewModel.studentDetailResponse.collectAsState()

    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Detail Siswa",
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = {
                isRefreshing = true
                viewModel.fetchStudentDetail(studentId) // Memanggil ulang data detail siswa
                isRefreshing = false // Setel kembali ke false setelah selesai
            }
        ) {
            studentDetailResponse.value?.let { response ->
                val student = response.student
                Log.d("StudentDetailPage", "Displaying details for student: ${student.name}")

                var selectedTabIndex by remember { mutableStateOf(0) }
                val tabTitles = listOf("Prestasi", "Kehadiran", "Laporan Kesehatan", "Pelanggaran")

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF9FAFB)) // Set background color
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        InitialsAvatarDetailPage(name = student.name, modifier = Modifier.padding(end = 16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = student.name,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF09090B) // Set text color to #09090B
                                )
                            )
                            Text(
                                text = "Kelas ${student.classRoomName}",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF909096)
                                )
                            )
                        }
                    }

                    // Custom scrollable tab row with reduced height
                    val scrollState = rememberScrollState()
                    Row(
                        modifier = Modifier
                            .horizontalScroll(scrollState)
                            .fillMaxWidth()
                    ) {
                        tabTitles.forEachIndexed { index, title ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp, vertical = 4.dp) // Reduced vertical padding
                                    .background(
                                        color = if (selectedTabIndex == index) MaterialTheme.colorScheme.primary else Color(0xFFF2F5F9),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .clickable { selectedTabIndex = index }
                                    .padding(horizontal = 12.dp, vertical = 6.dp) // Padding inside the box
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

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color(0xFFF9FAFB) // Set background color to F9FAFB
                    ) {
                        when (selectedTabIndex) {
                            0 -> AchievementsContent(response.achievements.data)
                            1 -> AttendancesContent(response.attendances.data)
                            2 -> HealthReportsContent (response.healthReports.data)
                            3 -> ViolationsContent(response.violations.data)
                        }
                    }
                }
            } ?: run {
                Log.d("StudentDetailPage", "Student detail is null")
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
@Composable
fun AchievementsContent(achievements: List<Achievement>) {
    // Define the date format
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB)) // Light background for the whole screen
            .padding(4.dp)
    ){
        items(achievements) { achievement ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(1.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFFFFF) // Set background color to white
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "${achievement.category}",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF909096) // Consistent text color
                    )
                    Text(
                        text = "${achievement.achievementName}",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF09090B) // Consistent text color
                    )
                    // Format the date
                    val formattedDate = dateFormatter.format(achievement.date)

                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF909096)
                        )
                    )
                    Text(
                        text = "${achievement.description}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF09090B))
                        )
                }
            }
        }
    }
}

@Composable
fun AttendancesContent(attendances: List<Attendance>) {
    // Sort the attendance list from the most recent
    val sortedAttendances = attendances.sortedByDescending { it.date }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB)) // Light background for the whole screen
            .padding(4.dp)
    ) {
        items(sortedAttendances) { attendance ->
            AttendanceCard(attendance)
        }
    }
}

@Composable
fun AttendanceCard(attendance: Attendance) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = attendance.date.toFormattedString(),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF09090B) // Consistent text color
                )
                StatusBadge(attendance.status)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Jam Masuk: ${attendance.clockIn?.toFormattedTime() ?: "-"}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF909096)
                    )
                )
                Text(
                    text = "Jam Keluar: ${attendance.clockOut?.toFormattedTime() ?: "-"}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF909096)
                    )
                )
            }
            attendance.permissionReason?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Permission: $it",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Italic, // Set the text to italic
                        color = Color(0xFF909096)
                    )
                )
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val backgroundColor = when (status) {
        "present" -> Color(0xFFDFF0D8)
        "absent" -> Color(0xFFF2DEDE)
        "late" -> Color(0xFFFFF0B3)
        else -> Color(0xFFE0E0E0)
    }
    val textColor = when (status) {
        "present" -> Color(0xFF3C763D)
        "absent" -> Color(0xFFA94442)
        "late" -> Color(0xFF8A6D3B)
        else -> Color(0xFF666666)
    }

    Box(
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = status.capitalize(),
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

fun Date.toFormattedString(): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.format(this)
}

fun Time?.toFormattedTime(): String {
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return this?.let { format.format(it) } ?: "-"
}

@Composable
fun HealthReportsContent(healthReports: List<HealthReport>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB)) // Light background for the whole screen
            .padding(8.dp)
    ) {
        items(healthReports) { report ->
            HealthReportCard(report)
        }
    }
}

@Composable
fun HealthReportCard(report: HealthReport) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFF9FAFB)),
                elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Tanggal Laporan: ${report.reportDate}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Status Kesehatan: ${report.healthStatus}",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color(0xFF909096)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Gejala: ${report.symptoms}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF09090B),
                    fontStyle = FontStyle.Italic
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Catatan Dokter: ${report.doctorsNotes}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF09090B)
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ViolationsContent(violations: List<Violation>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB)) // Light background for the whole screen
            .padding(8.dp)
    ) {
        items(violations) { violation ->
            ViolationCard(violation)
        }
    }
}

@Composable
fun ViolationCard(violation: Violation) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFF9FAFB)),
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Jenis Pelanggaran: ${violation.violationType}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Keterangan: ${violation.description}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF09090B)
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tanggal: ${violation.createdAt.toFormattedString()}",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF909096)
                )
            )
        }
    }
}

@Composable
fun InitialsAvatarDetailPage(name: String, modifier: Modifier = Modifier) {
    // Menghitung inisial dari nama
    val initials = name.split(" ").joinToString("") { it.take(1) }.take(2).uppercase()

    Box(
        modifier = modifier
            .size(80.dp) // Ukuran lingkaran
            .clip(CircleShape) // Membuat bentuk lingkaran
            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape) // Tambahkan border
            .background(Color(0xFFE6ECF8)), // Warna latar belakang lingkaran
        contentAlignment = Alignment.Center // Menyelaraskan konten di tengah
    ) {
        Text(
            text = initials,
            color = MaterialTheme.colorScheme.primary, // Warna teks
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
}