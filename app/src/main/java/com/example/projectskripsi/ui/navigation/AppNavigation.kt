package com.example.projectskripsi.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectskripsi.ui.screen.AchievementPage
import com.example.projectskripsi.ui.screen.AnnouncementDetailPage
import com.example.projectskripsi.ui.screen.AnnouncementDetailViewModel
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
import com.example.projectskripsi.ui.screen.StudentDetailPage
import com.example.projectskripsi.ui.screen.StudentListPage
import com.example.projectskripsi.ui.screen.StudentViewModel
import com.example.projectskripsi.ui.screen.ViolationPage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    announcementViewModel: AnnouncementViewModel,
    studentViewModel: StudentViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "student_list") {
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

        composable("announcement_page") {
            AnnouncementPage(navController = navController, viewModel = announcementViewModel, modifier = Modifier.fillMaxSize())
        }

        composable(
            "announcement_detail/{announcementId}",
            arguments = listOf(navArgument("announcementId") { type = NavType.IntType })
        ) { backStackEntry ->
            val announcementId = backStackEntry.arguments?.getInt("announcementId") ?: return@composable
            val detailViewModel = AnnouncementDetailViewModel()
            AnnouncementDetailPage(navController = navController, viewModel = detailViewModel, announcementId = announcementId)
        }

        composable("attendance_page"){
            AttendancePage(navController = navController, modifier = Modifier.fillMaxSize())
        }

        composable("health_report_page"){
            HealthReportPage(navController = navController, modifier = Modifier.fillMaxSize())
        }

        composable("student_list") {
            StudentListPage(navController = navController, viewModel = studentViewModel, modifier = Modifier.fillMaxSize())
        }

        composable(
            "student_detail/{studentId}",
            arguments = listOf(navArgument("studentId") { type = NavType.IntType })
        ) { backStackEntry ->
            val studentId = backStackEntry.arguments?.getInt("studentId") ?: return@composable
            StudentDetailPage(navController = navController, viewModel = studentViewModel, studentId = studentId)
        }

        composable("violation_page"){
            ViolationPage(navController = navController, modifier = Modifier.fillMaxSize())
        }
    }
}

