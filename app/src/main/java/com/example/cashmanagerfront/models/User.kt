package com.example.cashmanagerfront.models

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class User(
    val userId: Int,
    val token: String
)
