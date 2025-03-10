package com.example.projectskripsi.ui.screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectskripsi.data.model.AnnouncementDetail
import com.example.projectskripsi.data.repository.AnnouncementRepository
import kotlinx.coroutines.launch

class AnnouncementDetailViewModel : ViewModel() {

    private val repository = AnnouncementRepository()

    var selectedAnnouncement = mutableStateOf<AnnouncementDetail?>(null)
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    private val TAG = "AnnouncementDetailVM"

    fun getAnnouncementDetail(id: Int) {
        Log.d(TAG, "getAnnouncementDetail: Memulai pengambilan detail untuk ID $id")
        isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getAnnouncementDetail(id)
                if (response.isSuccessful) {
                    val announcement = response.body()?.data
                    if (announcement != null) {
                        selectedAnnouncement.value = announcement
                        Log.d(TAG, "getAnnouncementDetail: Detail berhasil diambil untuk ID $id")
                    } else {
                        errorMessage.value = "Data tidak ditemukan"
                        Log.e(TAG, "getAnnouncementDetail: Data tidak ditemukan untuk ID $id")
                    }
                } else {
                    errorMessage.value = "Gagal memuat detail"
                    Log.e(TAG, "getAnnouncementDetail: Gagal memuat detail, response code: ${response.code()}")
                }
            } catch (e: Exception) {
                errorMessage.value = "Terjadi kesalahan: ${e.message}"
                Log.e(TAG, "getAnnouncementDetail: Terjadi kesalahan", e)
            } finally {
                isLoading.value = false
                Log.d(TAG, "getAnnouncementDetail: Pengambilan detail selesai untuk ID $id")
            }
        }
    }
}