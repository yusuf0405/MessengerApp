package com.example.chatapp.login_screen.domain.usecase

import com.example.chatapp.login_screen.domain.repository.LoginRepository
import javax.inject.Inject

class LoginInUseCase @Inject constructor(private val repository: LoginRepository) {
    suspend fun execute(email: String, password: String) =
        repository.login(email = email, password = password)
}