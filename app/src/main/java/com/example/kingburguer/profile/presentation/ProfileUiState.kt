package com.example.kingburguer.profile.presentation

import com.example.kingburguer.profile.data.ProfileResponse

data class ProfileUiState(
    val isLoading: Boolean = false,
    val profile: ProfileResponse? = null,
    val error: String? = null
)
