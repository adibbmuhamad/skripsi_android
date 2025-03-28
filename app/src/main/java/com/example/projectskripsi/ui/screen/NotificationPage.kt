package com.example.projectskripsi.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun NotificationPage(navController: NavController, modifier: Modifier = Modifier) {

}

@Preview
@Composable
private fun NotificationPagePreview() {
    OnBoarding1Page(navController = NavHostController(LocalContext.current))


}