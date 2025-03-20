package com.example.projectskripsi.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    var selectedClass by remember { mutableStateOf("All Classes") }
    var expanded by remember { mutableStateOf(false) }

    // Get unique class names
    val classNames = students.value.map { it.classRoomName }.distinct().sorted()

    // Filtered list based on search query and selected class
    val filteredStudents = students.value.filter {
        (selectedClass == "All Classes" || it.classRoomName == selectedClass) &&
                it.name.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = modifier.fillMaxSize().padding(16.dp, top = 40.dp)) {
        // Search and Filter UI
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search by Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                readOnly = true,
                value = selectedClass,
                onValueChange = {},
                label = { Text("Filter by Class") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("All Classes") },
                    onClick = {
                        selectedClass = "All Classes"
                        expanded = false
                    }
                )
                classNames.forEach { className ->
                    DropdownMenuItem(
                        text = { Text(className) },
                        onClick = {
                            selectedClass = className
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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
                modifier = Modifier
                    .fillMaxSize()
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
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("student_detail/${student.id}")
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InitialsAvatar(name = student.name, modifier = Modifier.padding(end = 16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = student.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "NISN: ${student.nisn}",
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                )
                Text(
                    text = "Class: ${student.classRoomName}",
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                )
            }
        }
    }
}