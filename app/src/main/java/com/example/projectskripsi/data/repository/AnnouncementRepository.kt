package com.example.projectskripsi.data.repository

import android.util.Log
import com.example.projectskripsi.data.model.AnnouncementDetailResponse
import com.example.projectskripsi.data.model.AnnouncementResponse
import com.example.projectskripsi.data.network.RetrofitClient
import retrofit2.Response

class AnnouncementRepository {

    // Tag untuk logging
    private val TAG = "AnnouncementRepository"

    // Mendapatkan daftar pengumuman
    suspend fun getAnnouncements(): Response<AnnouncementResponse> {
        return RetrofitClient.apiService.getAnnouncements()
    }

}