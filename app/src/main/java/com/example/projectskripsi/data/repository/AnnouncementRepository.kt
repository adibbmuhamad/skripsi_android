package com.example.projectskripsi.data.repository

import com.example.projectskripsi.data.model.Announcement
import com.example.projectskripsi.data.model.AnnouncementResponse
import com.example.projectskripsi.data.network.RetrofitClient
import retrofit2.Response

class AnnouncementRepository {

    // Mendapatkan daftar pengumuman
    suspend fun getAnnouncements(): Response<AnnouncementResponse> {
        return RetrofitClient.apiService.getAnnouncements()
    }

    // Mendapatkan detail pengumuman berdasarkan ID
    suspend fun getAnnouncementDetail(id: Int): Response<Announcement> {
        return RetrofitClient.apiService.getAnnouncementDetail(id)
    }
}