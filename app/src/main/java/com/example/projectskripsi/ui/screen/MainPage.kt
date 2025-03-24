package com.example.projectskripsi.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.projectskripsi.R

@Composable
fun MainPage(navController: NavController, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // First Column for first 3 buttons
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FeatureButton(
                iconRes = R.drawable.ic_trophy,
                label = "Achievements",
                onClick = {
                    navController.navigate("achievement_page")
                }
            )

            FeatureButton(
                iconRes = R.drawable.ic_megaphone,
                label = "Announcements",
                onClick = {
                    navController.navigate("announcement_page")
                }
            )

            FeatureButton(
                iconRes = R.drawable.ic_user,
                label = "Attendances",
                onClick = {
                    navController.navigate("attendance_page")
                }
            )
        }

        Spacer(modifier = Modifier.width(24.dp))

        // Second Column for last 3 buttons
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FeatureButton(
                iconRes = R.drawable.ic_heart,
                label = "Health Reports",
                onClick = {
                    navController.navigate("health_report_page")
                }
            )

            FeatureButton(
                iconRes = R.drawable.ic_academic,
                label = "Students",
                onClick = {
                    navController.navigate("student_list")
                }
            )

            FeatureButton(
                iconRes = R.drawable.ic_x_circle,
                label = "Violations",
                onClick = {
                    navController.navigate("violation_page")
                }
            )
        }
    }
}

@Composable
private fun FeatureButton(
    iconRes: Int,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            IconButton(
                onClick = onClick,
                modifier = Modifier.size(72.dp)
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null, // Decorative image
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MainPagePreview() {
    MaterialTheme {
        MainPage(navController = NavHostController(LocalContext.current))
    }
}