package com.example.projectskripsi.ui.screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NotificationNavigationViewModel : ViewModel() {
    private val _shouldNavigateToNotifications = MutableStateFlow(false)
    val shouldNavigateToNotifications = _shouldNavigateToNotifications.asStateFlow()

    fun navigateToNotifications() {
        _shouldNavigateToNotifications.value = true
    }

    fun navigationComplete() {
        _shouldNavigateToNotifications.value = false
    }
}