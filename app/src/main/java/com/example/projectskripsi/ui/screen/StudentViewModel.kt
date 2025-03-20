package com.example.projectskripsi.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectskripsi.data.model.Student
import com.example.projectskripsi.data.model.StudentDetail
import com.example.projectskripsi.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StudentViewModel : ViewModel() {

    private val _students = MutableStateFlow<List<Student>>(emptyList())
    val students: StateFlow<List<Student>> = _students

    private val _studentDetail = MutableStateFlow<StudentDetail?>(null)
    val studentDetail: StateFlow<StudentDetail?> = _studentDetail

    fun fetchStudents() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getStudents()
                if (response.isSuccessful) {
                    _students.value = response.body()?.data ?: emptyList()
                    Log.d("StudentViewModel", "Fetched students successfully")
                } else {
                    Log.e("StudentViewModel", "Failed to fetch students: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("StudentViewModel", "Error fetching students", e)
            }
        }
    }

    fun fetchStudentDetail(id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getStudentDetail(id)
                if (response.isSuccessful) {
                    _studentDetail.value = response.body()?.student
                    Log.d("StudentViewModel", "Fetched student detail successfully for ID: $id")
                } else {
                    Log.e("StudentViewModel", "Failed to fetch student detail: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("StudentViewModel", "Error fetching student detail for ID: $id", e)
            }
        }
    }
}