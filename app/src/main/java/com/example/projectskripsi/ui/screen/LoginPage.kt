package com.example.projectskripsi.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun LoginPage(navController: NavController, modifier: Modifier = Modifier) {

}

@Preview
@Composable
private fun LoginPagePreview() {
    LoginPage(navController = NavHostController(LocalContext.current))


}