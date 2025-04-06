package com.example.projectskripsi.data.repository

import com.example.projectskripsi.data.model.AchievementResponse
import com.example.projectskripsi.data.network.RetrofitClient
import retrofit2.Response

class AchievementListRepository {

    // Tag untuk logging
    private val TAG = "AchievementRepository"

    // Mendapatkan daftar pencapaian
    suspend fun getAchievements(): Response<AchievementResponse> {
        return RetrofitClient.apiService.getAchievements()
    }
}