package com.example.projectskripsi.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun StudentListPage(navController: NavController, modifier: Modifier = Modifier, viewModel: StudentViewModel) {
    // Memanggil fetchStudents saat composable ini dimulai
    LaunchedEffect(Unit) {
        viewModel.fetchStudents()
    }

    val students = viewModel.students.collectAsState()

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        students.value.forEach { student ->
            Text(
                text = student.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("student_detail/${student.id}")
                    }
                    .padding(8.dp)
            )
            Divider()
        }
    }
}