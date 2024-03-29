package com.example.cashmanagerfront.activities.login

/**
 * Data validation state of the login form.
 */
data class LoginFormState(
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val serverError: Int? = null,
    val isDataValid: Boolean = false
)
