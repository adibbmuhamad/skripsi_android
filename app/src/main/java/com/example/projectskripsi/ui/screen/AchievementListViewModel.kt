package com.example.projectskripsi.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectskripsi.data.model.AchievementList
import com.example.projectskripsi.data.repository.AchievementListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AchievementViewModel : ViewModel() {

    private val repository = AchievementListRepository()

    // StateFlow untuk menyimpan daftar pencapaian
    private val _achievements = MutableStateFlow<List<AchievementList>>(emptyList())
    val achievements: StateFlow<List<AchievementList>> = _achievements.asStateFlow()

    // StateFlow untuk menyimpan status loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // StateFlow untuk menyimpan pesan error
    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    // Tag untuk logging
    private val TAG = "AchievementViewModel"

    // Mendapatkan daftar pencapaian
    fun getAchievements() {
        Log.d(TAG, "getAchievements: Memulai pengambilan data")
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getAchievements()
                if (response.isSuccessful) {
                    Log.d(TAG, "getAchievements: Data berhasil diambil")
                    val achievementResponse = response.body()
                    _achievements.value = achievementResponse?.data ?: emptyList()
                } else {
                    _errorMessage.value = "Gagal memuat data"
                    Log.e(TAG, "getAchievements: Gagal memuat data, response code: ${response.code()}")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
                Log.e(TAG, "getAchievements: Terjadi kesalahan", e)
            } finally {
                _isLoading.value = false
                Log.d(TAG, "getAchievements: Pengambilan data selesai")
            }
        }
    }
}