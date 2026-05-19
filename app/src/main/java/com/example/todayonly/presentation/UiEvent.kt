package com.example.todayonly.presentation

sealed interface UiEvent {

    data class ShowSnackBar(
        val message: String,
        val actionLabel: String? = null,
        val onAction: (() -> Unit)? = null
    ) : UiEvent
}