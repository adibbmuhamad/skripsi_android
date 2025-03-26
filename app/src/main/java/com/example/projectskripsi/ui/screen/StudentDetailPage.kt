package com.example.projectskripsi.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectskripsi.data.model.Achievement
import com.example.projectskripsi.data.model.Attendance
import com.example.projectskripsi.data.model.HealthReport
import com.example.projectskripsi.data.model.Violation
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun StudentDetailPage(navController: NavController, viewModel: StudentViewModel, studentId: Int) {
    LaunchedEffect(studentId) {
        viewModel.fetchStudentDetail(studentId)
    }

    val studentDetailResponse = viewModel.studentDetailResponse.collectAsState()

    studentDetailResponse.value?.let { response ->
        val student = response.student
        Log.d("StudentDetailPage", "Displaying details for student: ${student.name}")

        var selectedTabIndex by remember { mutableStateOf(0) }
        val tabTitles = listOf("Achievements", "Attendances", "Health Reports", "Violations")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB)) // Set background color
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Student Details",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

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

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFFF9FAFB) // Set background color to F9FAFB
            ) {
                when (selectedTabIndex) {
                    0 -> AchievementsContent(response.achievements.data)
                    1 -> AttendancesContent(response.attendances.data)
                    2 -> HealthReportsContent(response.healthReports.data)
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

@Composable
fun AchievementsContent(achievements: List<Achievement>) {
    // Define the date format
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    LazyColumn {
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
    LazyColumn {
        items(attendances) { attendance ->
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
                        text = "Date: ${attendance.date}",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF09090B) // Consistent text color
                    )
                    Text(text = "Status: ${attendance.status}")
                }
            }
        }
    }
}

@Composable
fun HealthReportsContent(healthReports: List<HealthReport>) {
    LazyColumn {
        items(healthReports) { report ->
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
                        text = "Report Date: ${report.reportDate}",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF09090B) // Consistent text color
                    )
                    Text(text = "Health Status: ${report.healthStatus}")
                }
            }
        }
    }
}

@Composable
fun ViolationsContent(violations: List<Violation>) {
    LazyColumn {
        items(violations) { violation ->
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
                        text = "Violation Type: ${violation.violationType}",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF09090B) // Consistent text color
                    )
                    Text(text = "Description: ${violation.description}")
                }
            }
        }
    }
}