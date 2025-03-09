package com.example.projectskripsi.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projectskripsi.ui.screen.LoginPage
import com.example.projectskripsi.ui.screen.MainPage
import com.example.projectskripsi.ui.screen.OnBoarding1Page
import com.example.projectskripsi.ui.screen.OnBoarding2Page
import com.example.projectskripsi.ui.screen.OnBoarding3Page
import com.example.projectskripsi.ui.screen.ProfilePage
import com.example.projectskripsi.ui.screen.SplashScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash_screen") {
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
    }
}

