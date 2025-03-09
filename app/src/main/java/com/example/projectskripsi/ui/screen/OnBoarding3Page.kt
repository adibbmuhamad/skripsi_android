package com.example.projectskripsi.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun OnBoarding3Page(navController: NavController, modifier: Modifier = Modifier) {

}

@Preview
@Composable
private fun OnBoarding3PagePreview() {
    OnBoarding3Page(navController = NavHostController(LocalContext.current))



}