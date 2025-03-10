package com.example.projectskripsi.data.network

import com.example.projectskripsi.data.model.Announcement
import com.example.projectskripsi.data.model.AnnouncementResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    // Mendapatkan daftar pengumuman
    @GET("api/announcements")
    suspend fun getAnnouncements(): Response<AnnouncementResponse>

    // Mendapatkan detail pengumuman berdasarkan ID
    @GET("api/announcements/{id}")
    suspend fun getAnnouncementDetail(@Path("id") id: Int): Response<Announcement>
}