package com.example.projectskripsi.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projectskripsi.ui.screen.AchievementPage
import com.example.projectskripsi.ui.screen.AnnouncementPage
import com.example.projectskripsi.ui.screen.AnnouncementViewModel
import com.example.projectskripsi.ui.screen.AttendancePage
import com.example.projectskripsi.ui.screen.HealthReportPage
import com.example.projectskripsi.ui.screen.LoginPage
import com.example.projectskripsi.ui.screen.MainPage
import com.example.projectskripsi.ui.screen.OnBoarding1Page
import com.example.projectskripsi.ui.screen.OnBoarding2Page
import com.example.projectskripsi.ui.screen.OnBoarding3Page
import com.example.projectskripsi.ui.screen.ProfilePage
import com.example.projectskripsi.ui.screen.SplashScreen
import com.example.projectskripsi.ui.screen.StudentPage
import com.example.projectskripsi.ui.screen.ViolationPage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(viewModel: AnnouncementViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main_page") {
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }

        composable("onboarding1_screen") {
            OnBoarding1Page(navController = navController, modifier = Modifier.fillMaxSize())
        }

        composable("onboarding2_screen") {
            OnBoarding2Page(navController = navController, modifier = Modifier.fillMaxSize())
        }

        composable("onboarding3_screen") {
            OnBoarding3Page(navController = navController, modifier = Modifier.fillMaxSize())
        }

        composable("profile_page"){
            ProfilePage(navController = navController, modifier = Modifier.fillMaxSize())
        }

        composable("main_page"){
            MainPage(navController = navController, modifier = Modifier.fillMaxSize())
        }

        composable("login_screen"){
            LoginPage(navController = navController, modifier = Modifier.fillMaxSize())
        }

        composable("achievement_page"){
            AchievementPage(navController = navController, modifier = Modifier.fillMaxSize())
        }

        composable("announcement_page"){
            AnnouncementPage(navController = navController, viewModel = viewModel, modifier = Modifier.fillMaxSize())
        }

        composable("attendance_page"){
            AttendancePage(navController = navController, modifier = Modifier.fillMaxSize())
        }

        composable("health_report_page"){
            HealthReportPage(navController = navController, modifier = Modifier.fillMaxSize())
        }

        composable("student_page"){
            StudentPage(navController = navController, modifier = Modifier.fillMaxSize())
        }

        composable("violation_page"){
            ViolationPage(navController = navController, modifier = Modifier.fillMaxSize())
        }
    }
}

