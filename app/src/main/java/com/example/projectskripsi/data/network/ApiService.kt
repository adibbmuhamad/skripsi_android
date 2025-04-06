package com.example.projectskripsi.data.network

import com.example.projectskripsi.data.model.AchievementResponse
import com.example.projectskripsi.data.model.AnnouncementDetailResponse
import com.example.projectskripsi.data.model.AnnouncementResponse
import com.example.projectskripsi.data.model.ClassroomsResponse
import com.example.projectskripsi.data.model.StudentDetailResponse
import com.example.projectskripsi.data.model.StudentsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    // Mendapatkan daftar pengumuman
    @GET("api/announcements")
    suspend fun getAnnouncements(): Response<AnnouncementResponse>

    // Mendapatkan detail pengumuman berdasarkan ID
    @GET("api/announcements/{id}")
    suspend fun getAnnouncementDetail(@Path("id") id: Int): Response<AnnouncementDetailResponse>

    @GET("api/students")
    suspend fun getStudents(): Response<StudentsResponse>

    @GET("api/student/{id}/detail")
    suspend fun getStudentDetail(@Path("id") id: Int): Response<StudentDetailResponse>

    // Mendapatkan daftar kelas
    @GET("api/classrooms")
    suspend fun getClassrooms(): Response<ClassroomsResponse>

    // Mendapatkan daftar pencapaian
    @GET("api/achievements")
    suspend fun getAchievements(): Response<AchievementResponse>


}