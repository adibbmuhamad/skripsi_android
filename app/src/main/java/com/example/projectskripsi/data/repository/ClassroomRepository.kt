package com.example.projectskripsi.data.repository

import com.example.projectskripsi.data.model.ClassroomsResponse
import com.example.projectskripsi.data.network.RetrofitClient
import retrofit2.Response

class ClassroomRepository {

    // Tag untuk logging
    private val TAG = "ClassroomRepository"

    // Mendapatkan daftar kelas
    suspend fun getClassrooms(): Response<ClassroomsResponse> {
        return RetrofitClient.apiService.getClassrooms()
    }
}