package com.example.cashmanagerfront.data

import com.example.cashmanagerfront.helpers.Result
import com.example.cashmanagerfront.models.User
import com.example.cashmanagerfront.objects.api.Api
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(email: String, password: String): Result<User> {
        try {
            val result = Api.signIn(email, password)

            val user = User(result, Api.token!!)

            return Result.Success(user)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
        Api.token = null
        this.logout()
    }
}

