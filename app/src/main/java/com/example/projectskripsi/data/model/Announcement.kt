package com.example.projectskripsi.data.model

import com.google.gson.annotations.SerializedName

data class AnnouncementResponse(
    val data: List<Announcement>
    // Anda dapat menambahkan properti lain seperti `links` dan `meta` jika diperlukan
)

data class Announcement(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("category") val category: String,
    @SerializedName("description") val description: String
)
