package com.example.projectskripsi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projectskripsi.ui.navigation.AppNavigation
import com.example.projectskripsi.ui.screen.AnnouncementPage
import com.example.projectskripsi.ui.screen.AnnouncementViewModel
import com.example.projectskripsi.ui.screen.StudentViewModel
import com.example.projectskripsi.ui.theme.ProjectSkripsiTheme

class MainActivity : ComponentActivity() {

    private val announcementViewModel: AnnouncementViewModel by viewModels()
    private val studentViewModel: StudentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectSkripsiTheme {
                AppNavigation(
                    announcementViewModel = announcementViewModel,
                    studentViewModel = studentViewModel // Berikan ViewModel siswa ke AppNavigation
                )
            }
        }
    }
}

@Preview
@Composable
private fun AnnouncementPagePreview() {
    // Gunakan Preview untuk melihat tampilannya
    AnnouncementPage(
        navController = NavController(LocalContext.current),
        viewModel = AnnouncementViewModel()
    )
}