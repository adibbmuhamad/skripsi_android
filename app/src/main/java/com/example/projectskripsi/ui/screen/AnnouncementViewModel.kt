package com.example.projectskripsi.ui.screen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectskripsi.data.model.Announcement
import com.example.projectskripsi.data.repository.AnnouncementRepository
import kotlinx.coroutines.launch

class AnnouncementViewModel : ViewModel() {

    private val repository = AnnouncementRepository()

    // State untuk menyimpan daftar pengumuman
    var announcements = mutableStateListOf<Announcement>()
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    // Tag untuk logging
    private val TAG = "AnnouncementViewModel"

    // Mendapatkan daftar pengumuman
    fun getAnnouncements() {
        Log.d(TAG, "getAnnouncements: Memulai pengambilan data")
        isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getAnnouncements()
                if (response.isSuccessful) {
                    Log.d(TAG, "getAnnouncements: Data berhasil diambil")
                    val announcementResponse = response.body()
                    announcements.clear()
                    announcements.addAll(announcementResponse?.data ?: emptyList())
                } else {
                    errorMessage.value = "Gagal memuat data"
                    Log.e(TAG, "getAnnouncements: Gagal memuat data, response code: ${response.code()}")
                }
            } catch (e: Exception) {
                errorMessage.value = "Terjadi kesalahan: ${e.message}"
                Log.e(TAG, "getAnnouncements: Terjadi kesalahan", e)
            } finally {
                isLoading.value = false
                Log.d(TAG, "getAnnouncements: Pengambilan data selesai")
            }
        }
    }
}