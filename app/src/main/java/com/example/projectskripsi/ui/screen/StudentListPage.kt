package com.example.projectskripsi.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectskripsi.data.model.Student
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.ScrollState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentListPage(navController: NavController, modifier: Modifier = Modifier, viewModel: StudentViewModel) {
    // Memanggil fetchStudents saat composable ini dimulai
    LaunchedEffect(Unit) {
        viewModel.fetchStudents()
    }

    val students = viewModel.students.collectAsState()
    val isLoading = remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedTabIndex by remember { mutableStateOf(0) }

    // Get unique class names
    val classNames = students.value.map { it.classRoomName }.distinct().sorted()

    // Filtered list based on search query and selected class
    val filteredStudents = students.value.filter {
        it.classRoomName == classNames[selectedTabIndex] &&
                it.name.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB)) // Set background color
            .padding(horizontal = 16.dp, vertical = 24.dp) // Consistent padding
    ) {
        // Search UI with Icon
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search by Name") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp), // More rounded corners
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp)) // Consistent spacing

        // Custom scrollable tab row with reduced height
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .fillMaxWidth()
        ) {
            classNames.forEachIndexed { index, className ->
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
                        text = "Kelas: $className",
                        color = if (selectedTabIndex == index) Color.White else Color.Black,
                        fontSize = 14.sp, // Adjust text size if needed
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Consistent spacing

        if (isLoading.value) {
            // Tampilkan indikator loading
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (filteredStudents.isEmpty()) {
            // Tampilkan pesan jika daftar kosong
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No students available", fontSize = 18.sp, color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredStudents) { student ->
                    StudentItem(student, navController)
                }
            }
        }
    }

    // Simulasi loading selesai
    LaunchedEffect(students.value) {
        if (students.value.isNotEmpty()) {
            isLoading.value = false
        }
    }
}

@Composable
fun InitialsAvatar(name: String, modifier: Modifier = Modifier) {
    val initials = name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString("")
    Box(
        modifier = modifier
            .size(48.dp)
            .background(MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.medium),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun StudentItem(student: Student, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // Consistent vertical padding
            .clickable {
                navController.navigate("student_detail/${student.id}")
            },
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF) // Set background color to white
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Consistent padding inside card
            verticalAlignment = Alignment.CenterVertically
        ) {
            InitialsAvatar(name = student.name, modifier = Modifier.padding(end = 16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = student.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF09090B) // Set text color to #09090B
                    )
                )
                Text(
                    text = "Class: ${student.classRoomName}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF909096)
                    )
                )
            }
        }
    }
}