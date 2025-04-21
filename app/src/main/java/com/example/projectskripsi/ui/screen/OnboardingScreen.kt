package com.example.projectskripsi.ui.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.projectskripsi.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    currentPage: Int = 0,
    totalPages: Int = 3,
) {
    val pagerState = rememberPagerState(initialPage = currentPage, pageCount = { totalPages })
    val coroutineScope = rememberCoroutineScope()

    Log.d("OnboardingScreen", "OnboardingScreen initialized")

    Box(modifier = modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = true
        ) { page ->
            Log.d("OnboardingScreen", "Rendering page: $page")
            when (page) {
                0 -> FirstOnboardingPage(modifier)
                1 -> SecondOnboardingPage(modifier)
                2 -> ThirdOnboardingPage(modifier)
                else -> FirstOnboardingPage(modifier) // Fallback to first page
            }
        }

        // Page indicator dots
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(totalPages) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == pagerState.currentPage) Color(0xFF4285F4)
                                else Color(0xFFD8D8D8)
                            )
                    )
                }
            }
        }

// Navigation buttons
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), // Tambahkan padding horizontal untuk ruang di sisi
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tombol "Lewati"
                if (pagerState.currentPage < totalPages - 1) {
                    Text(
                        text = "Lewati",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .background(
                                color = Color.LightGray.copy(alpha = 0.2f), // Background abu-abu transparan
                                shape = CircleShape
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp) // Padding untuk membuatnya seperti tombol
                            .clickable {
                                Log.d("OnboardingScreen", "Skip button clicked")
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(totalPages - 1)
                                }
                            }
                    )
                }

                // Spacer untuk mengisi ruang antara tombol "Lewati" dan "Lanjut/Mulai"
                Spacer(modifier = Modifier.weight(1f))

                // Tombol "Lanjut" atau "Mulai"
                Text(
                    text = if (pagerState.currentPage < totalPages - 1) "Lanjut" else "Mulai",
                    color = Color(0xFF4285F4),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .background(
                            color = Color .LightGray.copy(alpha = 0.2f), // Background abu-abu transparan
                            shape = CircleShape
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp) // Padding untuk membuatnya seperti tombol
                        .clickable {
                            Log.d("OnboardingScreen", "Next/Finish button clicked")
                            if (pagerState.currentPage < totalPages - 1) {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            } else {
                                Log.d("OnboardingScreen", "Navigating to login_screen")
                                navController.navigate("login_screen") {
                                    popUpTo("onboarding_screen") { inclusive = true }
                                }
                            }
                        }
                )
            }
        }
    }
}

@Composable
fun FirstOnboardingPage(modifier: Modifier = Modifier) {
    Log.d("FirstOnboardingPage", "Rendering FirstOnboardingPage")
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Background shape dari drawable
        Image(
            painter = painterResource(id = R.drawable.shape_onboarding1),
            contentDescription = "Background Shape",
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .align(Alignment.TopCenter)
                .zIndex(0f), // Shape di belakang teks
            contentScale = ContentScale.FillBounds
        )

        // Onboarding Title and Subtitle
        Column(
            modifier = Modifier
                .align(Alignment.TopStart) // Posisikan di bagian atas
                .padding(top = 200.dp, start = 20.dp, end = 100.dp) // Sesuaikan padding
                .zIndex(1f), // Teks di atas shape
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Awali Perjalanan Baru Bersama OrtuConnect",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Box(
                modifier = Modifier
                    .width(240.dp)
                    .height(1.dp)
                    .background(Color.White.copy(alpha = 0.5f))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(end = 100.dp),
                text = "Ikuti dan dukung proses belajar anak Anda secara langsung!",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                ),
            )
        }

        // Illustration
        Image(
            painter = painterResource(id = R.drawable.ass_onboarding1),
            contentDescription = "Onboarding Illustration",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 100.dp)
                .size(300.dp)
                .zIndex(1f), // Gambar di atas shape
            contentScale = ContentScale.Fit
        )

        // Bottom content
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 150.dp)
                .zIndex(1f), // Teks di atas shape
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "OrtuConnect",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF09090B)
                ),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Jembatan komunikasi digital antara orangtua dan sekolah",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                ),
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
fun SecondOnboardingPage(modifier: Modifier = Modifier) {
    Log.d("SecondOnboardingPage", "Rendering SecondOnboardingPage")
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Background shape dari drawable
        Image(
            painter = painterResource(id = R.drawable.shape_onboarding2), // Ganti dengan shape yang sesuai
            contentDescription = "Background Shape",
            modifier = Modifier
                .fillMaxWidth()
                .height(207.dp)
                .align(Alignment.TopCenter)
                .zIndex(0f), // Shape di belakang teks
            contentScale = ContentScale.FillBounds
        )

        // Onboarding Title and Subtitle
        Column(
            modifier = Modifier
                .align(Alignment.TopStart) // Posisikan di bagian atas
                .padding(top = 200.dp, start = 20.dp, end = 20.dp) // Sesuaikan padding
                .zIndex(1f), // Teks di atas shape
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Pantau Perkembangan Siswa Secara Real-Time",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Box(
                modifier = Modifier
                    .width(240.dp)
                    .height(1.dp)
                    .background(Color.Black.copy(alpha = 0.5f))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Dari absensi hingga prestasi, semuanya bisa Anda akses kapan saja, di mana saja.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                ),
            )
        }

        // Illustration
        Image(
            painter = painterResource(id = R.drawable.ass_onboarding2),
            contentDescription = "Education Illustration",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 100.dp)
                .size(300.dp)
                .zIndex(1f), // Gambar di atas shape
            contentScale = ContentScale.Fit
        )

        // Bottom content
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 150.dp)
                .zIndex(1f), // Teks di atas shape
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Lihat betapa mudahnya mengawasi pendidikan anak Anda!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                ),
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
fun ThirdOnboardingPage(modifier: Modifier = Modifier) {
    Log.d("ThirdOnboardingPage", "Rendering ThirdOnboardingPage")
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Background shape dari drawable
        Image(
            painter = painterResource(id = R.drawable.shape_onboarding3), // Ganti dengan shape yang sesuai
            contentDescription = "Background Shape",
            modifier = Modifier
                .fillMaxWidth()
                .height(394.dp)
                .align(Alignment.TopCenter)
                .zIndex(0f), // Shape di belakang teks
            contentScale = ContentScale.FillBounds
        )

        // Onboarding Title and Subtitle
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter) // Posisikan di bagian atas tengah
                .padding(top = 200.dp, start = 100.dp, end = 20.dp) // Sesuaikan padding
                .zIndex(1f), // Teks di atas shape
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Semua Aktivitas Anak, Dalam Genggaman Anda",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                textAlign = TextAlign.End,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = "Ingkasan harian sekolah anak Anda, lengkap dan mudah diakses.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                ),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .width(240.dp)
                    .height(1.dp)
                    .background(Color.White.copy(alpha = 0.5f))
            )
        }

        // Illustration
        Image(
            painter = painterResource(id = R.drawable.ass_onboarding3),
            contentDescription = "Classroom Illustration",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 120.dp)
                .size(300.dp)
                .zIndex(1f), // Gambar di atas shape
            contentScale = ContentScale.Fit
        )

        // Bottom content
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 150.dp)
                .zIndex(1f), // Teks di atas shape
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Yuk mulai sekarang dan dukung perubahan positif!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                ),
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun FirstOnboardingPagePreview() {
    FirstOnboardingPage()
}

@Preview
@Composable
private fun SecondOnboardingPagePreview() {
    SecondOnboardingPage()
}

@Preview
@Composable
private fun ThirdOnboardingPagePreview() {
    ThirdOnboardingPage()
}