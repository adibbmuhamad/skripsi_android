package com.example.projectskripsi.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectskripsi.data.model.Achievement
import com.example.projectskripsi.data.model.Attendance
import com.example.projectskripsi.data.model.HealthReport
import com.example.projectskripsi.data.model.Violation

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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Student Details",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

//            Spacer(modifier = Modifier.height(16.dp))

//            Card(
//                modifier = Modifier.fillMaxWidth(),
//                elevation = CardDefaults.cardElevation(4.dp)
//            ) {
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text(text = "Name: ${student.name}", fontWeight = FontWeight.Bold)
//                    Text(text = "Email: ${student.parentEmail}")
//                    Text(text = "NISN: ${student.nisn}")
//                    Text(text = "Address: ${student.address}")
//                    Text(text = "Gender: ${student.gender}")
//                    Text(text = "Parent Name: ${student.parentName}")
//                    Text(text = "Phone Number: ${student.phoneNumber}")
//                }
//            }

            Spacer(modifier = Modifier.height(16.dp))

            TabRow(selectedTabIndex = selectedTabIndex) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
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
    LazyColumn {
        items(achievements) { achievement ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Achievement: ${achievement.achievementName}", fontWeight = FontWeight.Bold)
                    Text(text = "Description: ${achievement.description}")
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
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Date: ${attendance.date}", fontWeight = FontWeight.Bold)
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
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Report Date: ${report.reportDate}", fontWeight = FontWeight.Bold)
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
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Violation Type: ${violation.violationType}", fontWeight = FontWeight.Bold)
                    Text(text = "Description: ${violation.description}")
                }
            }
        }
    }
}