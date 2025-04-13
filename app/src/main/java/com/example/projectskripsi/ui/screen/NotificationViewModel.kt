package com.example.projectskripsi.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class NotificationViewModel : ViewModel() {
    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun loadNotifications() {
        viewModelScope.launch {
            try {
                val userId = auth.currentUser?.uid ?: return@launch

                firestore.collection("users")
                    .document(userId)
                    .collection("notifications")
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .addSnapshotListener { snapshot, e ->
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e)
                            return@addSnapshotListener
                        }

                        if (snapshot != null) {
                            val notificationList = snapshot.documents.mapNotNull { doc ->
                                val id = doc.id
                                val title = doc.getString("title") ?: ""
                                val message = doc.getString("message") ?: ""
                                val timestamp = doc.getLong("timestamp") ?: System.currentTimeMillis()
                                val isRead = doc.getBoolean("isRead") ?: false

                                NotificationItem(id, title, message, timestamp, isRead)
                            }
                            _notifications.value = notificationList
                        }
                    }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading notifications", e)
            }
        }
    }

    fun markAsRead(notificationId: String) {
        viewModelScope.launch {
            try {
                val userId = auth.currentUser?.uid ?: return@launch

                firestore.collection("users")
                    .document(userId)
                    .collection("notifications")
                    .document(notificationId)
                    .update("isRead", true)
                    .await()

                // Juga update di state lokal
                _notifications.value = _notifications.value.map {
                    if (it.id == notificationId) it.copy(isRead = true) else it
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error marking notification as read", e)
            }
        }
    }

    fun deleteNotification(notificationId: String) {
        viewModelScope.launch {
            try {
                val userId = auth.currentUser?.uid ?: return@launch

                firestore.collection("users")
                    .document(userId)
                    .collection("notifications")
                    .document(notificationId)
                    .delete()
                    .await()

                // Update state lokal
                _notifications.value = _notifications.value.filter { it.id != notificationId }
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting notification", e)
            }
        }
    }

    fun clearAllNotifications() {
        viewModelScope.launch {
            try {
                val userId = auth.currentUser?.uid ?: return@launch

                // Karena Firestore tidak mendukung delete collection,
                // kita perlu menghapus dokumen satu per satu
                val batch = firestore.batch()
                val notificationsRef = firestore.collection("users")
                    .document(userId)
                    .collection("notifications")

                val documents = notificationsRef.get().await()
                for (document in documents) {
                    batch.delete(document.reference)
                }

                batch.commit().await()

                // Update state lokal
                _notifications.value = emptyList()
            } catch (e: Exception) {
                Log.e(TAG, "Error clearing notifications", e)
            }
        }
    }

    companion object {
        private const val TAG = "NotificationViewModel"
    }
}