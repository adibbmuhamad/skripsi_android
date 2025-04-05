package com.example.projectskripsi.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ClassroomsResponse(
    @SerializedName("data") val data: List<Classrooms>
)

data class Classrooms(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("updated_at") val updatedAt: Date,
    @SerializedName("room_number") val roomNumber: String,
    @SerializedName("capacity") val capacity: Int,
    @SerializedName("class_teacher") val classTeacher: String
)