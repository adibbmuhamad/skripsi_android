package com.example.projectskripsi.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date
import java.sql.Time

data class StudentDetailResponse(
    @SerializedName("student") val student: StudentDetail,
    @SerializedName("achievements") val achievements: Achievements,
    @SerializedName("attendances") val attendances: Attendances,
    @SerializedName("healthReports") val healthReports: HealthReports,
    @SerializedName("violations") val violations: Violations
)

data class StudentDetail(
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

data class Achievements(
    @SerializedName("data") val data: List<Achievement>
)

data class Achievement(
    @SerializedName("id") val id: Int,
    @SerializedName("student_id") val studentId: Int,
    @SerializedName("achievement_name") val achievementName: String,
    @SerializedName("description") val description: String,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("updated_at") val updatedAt: Date,
    @SerializedName("date") val date: Date,
    @SerializedName("category") val category: String
)

data class Attendances(
    @SerializedName("data") val data: List<Attendance>
)

data class Attendance(
    @SerializedName("id") val id: Int,
    @SerializedName("student_id") val studentId: Int,
    @SerializedName("date") val date: Date,
    @SerializedName("status") val status: String,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("updated_at") val updatedAt: Date,
    @SerializedName("permission_reason") val permissionReason: String?,
    @SerializedName("clock_in") val clockIn: Time?,
    @SerializedName("clock_out") val clockOut: Time?
)

data class HealthReports(
    @SerializedName("data") val data: List<HealthReport>
)

data class HealthReport(
    @SerializedName("id") val id: Int,
    @SerializedName("student_id") val studentId: Int,
    @SerializedName("report_date") val reportDate: String,
    @SerializedName("report") val report: String,
    @SerializedName("health_status") val healthStatus: String,
    @SerializedName("symptoms") val symptoms: String,
    @SerializedName("doctors_notes") val doctorsNotes: String,
    @SerializedName("attachments") val attachments: String?,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("updated_at") val updatedAt: Date
)

data class Violations(
    @SerializedName("data") val data: List<Violation>
)

data class Violation(
    @SerializedName("id") val id: Int,
    @SerializedName("student_id") val studentId: Int,
    @SerializedName("violation_type") val violationType: String,
    @SerializedName("description") val description: String,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("updated_at") val updatedAt: Date
)