package com.example.projectskripsi.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class AchievementResponse(
    @SerializedName("data") val data: List<AchievementList>
)

data class AchievementList(
    @SerializedName("id") val id: Int,
    @SerializedName("student_name") val studentName: String,
    @SerializedName("class_room") val classRoom: String,
    @SerializedName("nisn") val nisn: String,
    @SerializedName("achievement_name") val achievementName: String,
    @SerializedName("category") val category: String,
    @SerializedName("description") val description: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)