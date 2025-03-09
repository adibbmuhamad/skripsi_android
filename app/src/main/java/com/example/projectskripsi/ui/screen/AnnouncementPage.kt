package com.example.projectskripsi.ui.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun AnnouncementPage(navController: NavController, modifier: Modifier = Modifier) {
    Text("hallo")
}

@Preview
@Composable
private fun AnnouncementPagePreview() {
    AnnouncementPage(navController = NavHostController(LocalContext.current))
}