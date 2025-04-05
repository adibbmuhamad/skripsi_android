package com.example.projectskripsi.ui.screen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectskripsi.data.model.Classrooms
import com.example.projectskripsi.data.repository.ClassroomRepository
import kotlinx.coroutines.launch

class ClassroomViewModel : ViewModel() {

    private val repository = ClassroomRepository()

    // State untuk menyimpan daftar kelas
    var classrooms = mutableStateListOf<Classrooms>()
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    // Tag untuk logging
    private val TAG = "ClassroomViewModel"

    // Mendapatkan daftar kelas
    fun getClassrooms() {
        Log.d(TAG, "getClassrooms: Memulai pengambilan data")
        isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getClassrooms()
                if (response.isSuccessful) {
                    Log.d(TAG, "getClassrooms: Data berhasil diambil")
                    val classroomResponse = response.body()
                    classrooms.clear()
                    classrooms.addAll(classroomResponse?.data ?: emptyList())
                } else {
                    errorMessage.value = "Gagal memuat data"
                    Log.e(TAG, "getClassrooms: Gagal memuat data, response code: ${response.code()}")
                }
            } catch (e: Exception) {
                errorMessage.value = "Terjadi kesalahan: ${e.message}"
                Log.e(TAG, "getClassrooms: Terjadi kesalahan", e)
            } finally {
                isLoading.value = false
                Log.d(TAG, "getClassrooms: Pengambilan data selesai")
            }
        }
    }
}