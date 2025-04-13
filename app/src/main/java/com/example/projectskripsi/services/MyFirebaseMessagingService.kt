package com.example.projectskripsi.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.projectskripsi.MainActivity
import com.example.projectskripsi.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onNewToken(token: String) {
        Log.d(TAG, "New FCM token: $token")
        // Kirim token ke server Anda
        sendRegistrationToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "Message received from: ${remoteMessage.from}")

        // Cek apakah pesan berisi data
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data: ${remoteMessage.data}")

            // Proses data di sini jika diperlukan
            handleDataMessage(remoteMessage.data)
        }

        // Cek apakah pesan berisi notifikasi
        remoteMessage.notification?.let {
            Log.d(TAG, "Notification title: ${it.title}")
            Log.d(TAG, "Notification body: ${it.body}")

            // Tampilkan notifikasi
            val title = it.title ?: "New Notification"
            val body = it.body ?: "You have a new notification"

            // Simpan notifikasi ke Firestore
            saveNotificationToFirestore(title, body)

            // Tampilkan notifikasi di device
            showNotification(title, body)
        }
    }

    private fun sendRegistrationToServer(token: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users")
                .document(userId)
                .update("fcmToken", token)
                .addOnSuccessListener {
                    Log.d(TAG, "FCM token updated successfully")
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Failed to update FCM token", e)
                }
        }
    }

    private fun handleDataMessage(data: Map<String, String>) {
        // Proses data payload di sini
        val title = data["title"] ?: "New Message"
        val message = data["message"] ?: "You have a new message"

        // Simpan notifikasi ke Firestore
        saveNotificationToFirestore(title, message)

        // Tampilkan notifikasi
        showNotification(title, message)
    }

    private fun saveNotificationToFirestore(title: String, message: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val notification = hashMapOf(
                "title" to title,
                "message" to message,
                "timestamp" to System.currentTimeMillis(),
                "isRead" to false
            )

            firestore.collection("users")
                .document(userId)
                .collection("notifications")
                .add(notification)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "Notification saved with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error saving notification", e)
                }
        } else {
            Log.w(TAG, "User not logged in, notification won't be saved to history")
        }
    }

    private fun showNotification(title: String, message: String) {
        // Intent untuk membuka activity saat notifikasi diklik
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            // Tambahkan data untuk membuka halaman notifikasi langsung
            putExtra("openNotifications", true)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
            .setSmallIcon(R.drawable.ic_stat_ic_notification) // Pastikan resource ini ada
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            try {
                notify(System.currentTimeMillis().toInt(), builder.build())
            } catch (e: SecurityException) {
                Log.e(TAG, "Notification permission not granted", e)
            }
        }
    }

    companion object {
        private const val TAG = "FCMService"
    }
}