package com.example.cashmanagerfront.activities.login

import com.example.cashmanagerfront.models.User

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: User? = null,
    val error: Int? = null
)
