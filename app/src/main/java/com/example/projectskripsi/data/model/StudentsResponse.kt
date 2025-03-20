package com.example.projectskripsi.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class StudentsResponse(
    @SerializedName("data") val data: List<Student>
)

data class Student(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("parent_email") val parentEmail: String,
    @SerializedName("nisn") val nisn: String,
    @SerializedName("address") val address: String,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("updated_at") val updatedAt: Date,
    @SerializedName("class_room_id") val classRoomId: Int,
    @SerializedName("class_room_name") val classRoomName: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("parent_name") val parentName: String,
    @SerializedName("phone_number") val phoneNumber: String
)