package com.example.cashmanagerfront.activities.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.cashmanagerfront.repositories.LoginRepository
import com.example.cashmanagerfront.helpers.Result

import com.example.cashmanagerfront.R
import com.example.cashmanagerfront.models.User
import com.example.cashmanagerfront.objects.api.Api

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(email: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(email, password)

        if (result is Result.Success) {
            _loginResult.value =
                LoginResult(success = result.data)
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    fun loginDataChanged(username: String, password: String, server: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(emailError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else if (!isServerSelected(server)){
            _loginForm.value = LoginFormState(isDataValid = true)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private fun isServerSelected(server: String): Boolean {
        return server.isNotBlank()
    }
}
