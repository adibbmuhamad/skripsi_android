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
import com.example.projectskripsi.ui.screen.AnnouncementPage
import com.example.projectskripsi.ui.screen.AnnouncementViewModel
import com.example.projectskripsi.ui.screen.AuthState
import com.example.projectskripsi.ui.screen.AuthViewModel
import com.example.projectskripsi.ui.screen.ClassroomListPage
import com.example.projectskripsi.ui.screen.LoginPage
import com.example.projectskripsi.ui.screen.MainPage
import com.example.projectskripsi.ui.screen.NotificationNavigationViewModel
import com.example.projectskripsi.ui.screen.NotificationPage
import com.example.projectskripsi.ui.screen.OnboardingScreen
import com.example.projectskripsi.ui.screen.ProfilePage
import com.example.projectskripsi.ui.screen.SplashScreen
import com.example.projectskripsi.ui.screen.StudentDetailPage
import com.example.projectskripsi.ui.screen.StudentListPage
import com.example.projectskripsi.ui.screen.StudentViewModel


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

            composable("onboarding_screen") {
                OnboardingScreen(navController = navController, modifier = Modifier.fillMaxSize())
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

            composable("notification_page"){
                NotificationPage(navController = navController, modifier = Modifier.fillMaxSize(), notificationViewModel = viewModel())
            }

            // Tambahkan rute untuk halaman daftar kelas
            composable("classroom_list_page") {
                ClassroomListPage(navController = navController, modifier = Modifier.fillMaxSize())
            }
        }
    }}