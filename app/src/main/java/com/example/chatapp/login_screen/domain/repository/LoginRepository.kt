package com.example.chatapp.login_screen.domain.repository

import com.example.chatapp.app.utils.Resource
import com.google.firebase.auth.AuthResult

interface LoginRepository {
    suspend fun login(email: String, password: String): Resource<AuthResult>
}