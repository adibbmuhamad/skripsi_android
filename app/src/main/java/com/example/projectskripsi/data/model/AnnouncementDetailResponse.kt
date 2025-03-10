package com.example.projectskripsi.data.model

import com.google.gson.annotations.SerializedName

data class AnnouncementDetailResponse(
    @SerializedName("data")
    val data: AnnouncementDetail
)

data class AnnouncementDetail(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)