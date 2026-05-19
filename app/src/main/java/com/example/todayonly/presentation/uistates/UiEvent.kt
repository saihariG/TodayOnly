package com.example.todayonly.presentation.uistates

sealed interface UiEvent {

    data class ShowSnackBar(
        val message: String,
        val actionLabel: String? = null,
        val onAction: (() -> Unit)? = null
    ) : UiEvent
}