package com.example.projectskripsi.data.model

import com.google.gson.annotations.SerializedName

data class AnnouncementResponse(
    val data: List<Announcement>
    // Anda dapat menambahkan properti lain seperti `links` dan `meta` jika diperlukan
)

data class Announcement(
    val id: Int,
    val title: String,
    val body: String,
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)
