package com.example.projectskripsi.ui.navigation

import BottomNavigationBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectskripsi.ui.screen.AchievementListPage
import com.example.projectskripsi.ui.screen.AnnouncementDetailPage
import com.example.projectskripsi.ui.screen.AnnouncementDetailViewModel
import com.example.projectskripsi.ui.screen.AnnouncementPage
import com.example.projectskripsi.ui.screen.AnnouncementViewModel
import com.example.projectskripsi.ui.screen.AttendancePage
import com.example.projectskripsi.ui.screen.AuthState
import com.example.projectskripsi.ui.screen.AuthViewModel
import com.example.projectskripsi.ui.screen.ClassroomListPage
import com.example.projectskripsi.ui.screen.HealthReportPage
import com.example.projectskripsi.ui.screen.LoginPage
import com.example.projectskripsi.ui.screen.MainPage
import com.example.projectskripsi.ui.screen.NotificationNavigationViewModel
import com.example.projectskripsi.ui.screen.NotificationPage
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
    studentViewModel: StudentViewModel,
    authViewModel: AuthViewModel,
    onGoogleSignInClicked: () -> Unit,
    navigationViewModel: NotificationNavigationViewModel
) {
    val navController = rememberNavController()
    val authState by authViewModel.authState.collectAsState()
    val shouldNavigateToNotifications by navigationViewModel.shouldNavigateToNotifications.collectAsState()

    // Efek untuk memeriksa status autentikasi dan navigasi ke halaman yang sesuai
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.LoggedIn -> {
                // Jika pengguna sudah login, navigasi ke halaman utama
                navController.navigate("main_page") {
                    // Hapus stack navigasi agar pengguna tidak bisa kembali ke halaman login
                    popUpTo("login_screen") { inclusive = true }
                }
            }
            is AuthState.LoggedOut -> {
                // Jika pengguna keluar, navigasi ke halaman login
                navController.navigate("login_screen") {
                    // Hapus semua halaman sebelumnya dari stack
                    popUpTo(0) { inclusive = true }
                }
            }
            else -> {} // Tidak melakukan apa-apa untuk status lainnya
        }
    }

    LaunchedEffect(shouldNavigateToNotifications) {
        if (shouldNavigateToNotifications) {
            navController.navigate("notification_page")
            navigationViewModel.navigationComplete()
        }
    }

    Scaffold(
        bottomBar = {
            // Hanya tampilkan bottom bar jika pengguna sudah login
            if (authState is AuthState.LoggedIn) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "splash_screen", modifier = Modifier.padding(innerPadding)) {
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
                ProfilePage(
                    navController = navController,
                    authViewModel = authViewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable("main_page"){
                MainPage(navController = navController, viewModel = announcementViewModel, modifier = Modifier.fillMaxSize())
            }

            composable("login_screen"){
                LoginPage(
                    navController = navController,
                    authViewModel = authViewModel,
                    onGoogleSignInClicked = onGoogleSignInClicked,
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable("achievement_page"){
                AchievementListPage(navController = navController, modifier = Modifier.fillMaxSize())
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

            composable("notification_page"){
                NotificationPage(navController = navController, modifier = Modifier.fillMaxSize(), notificationViewModel = viewModel())
            }

            // Tambahkan rute untuk halaman daftar kelas
            composable("classroom_list_page") {
                ClassroomListPage(navController = navController, modifier = Modifier.fillMaxSize())
            }
        }
    }}