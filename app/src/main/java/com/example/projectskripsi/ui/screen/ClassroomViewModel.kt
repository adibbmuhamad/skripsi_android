package com.example.projectskripsi.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectskripsi.data.model.Classrooms
import com.example.projectskripsi.data.repository.ClassroomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClassroomViewModel : ViewModel() {

    private val repository = ClassroomRepository()

    // StateFlow untuk menyimpan daftar kelas
    private val _classrooms = MutableStateFlow<List<Classrooms>>(emptyList())
    val classrooms: StateFlow<List<Classrooms>> = _classrooms.asStateFlow()

    // StateFlow untuk menyimpan status loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // StateFlow untuk menyimpan pesan error
    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    // Tag untuk logging
    private val TAG = "ClassroomViewModel"

    // Mendapatkan daftar kelas
    fun getClassrooms() {
        Log.d(TAG, "getClassrooms: Memulai pengambilan data")
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getClassrooms()
                if (response.isSuccessful) {
                    Log.d(TAG, "getClassrooms: Data berhasil diambil")
                    val classroomResponse = response.body()
                    _classrooms.value = classroomResponse?.data ?: emptyList()
                } else {
                    _errorMessage.value = "Gagal memuat data"
                    Log.e(TAG, "getClassrooms: Gagal memuat data, response code: ${response.code()}")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
                Log.e(TAG, "getClassrooms: Terjadi kesalahan", e)
            } finally {
                _isLoading.value = false
                Log.d(TAG, "getClassrooms: Pengambilan data selesai")
            }
        }
    }
}