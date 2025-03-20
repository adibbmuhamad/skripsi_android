package com.example.projectskripsi.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun StudentDetailPage(navController: NavController, viewModel: StudentViewModel, studentId: Int) {
    // Memanggil fetchStudentDetail saat composable ini dimulai
    LaunchedEffect(studentId) {
        viewModel.fetchStudentDetail(studentId)
    }

    val studentDetail = viewModel.studentDetail.collectAsState()

    studentDetail.value?.let { student ->
        Log.d("StudentDetailPage", "Displaying details for student: ${student.name}")
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
            Divider()

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Name: ${student.name}", fontWeight = FontWeight.Bold)
                    Text(text = "Email: ${student.parentEmail}")
                    Text(text = "NISN: ${student.nisn}")
                    Text(text = "Address: ${student.address}")
                    Text(text = "Gender: ${student.gender}")
                    Text(text = "Parent Name: ${student.parentName}")
                    Text(text = "Phone Number: ${student.phoneNumber}")
                }
            }
        }
    } ?: run {
        Log.d("StudentDetailPage", "Student detail is null")
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Loading student details...", fontSize = 16.sp)
        }
    }
}