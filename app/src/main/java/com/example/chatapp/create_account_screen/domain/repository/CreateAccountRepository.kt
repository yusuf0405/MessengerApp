package com.example.chatapp.create_account_screen.domain.repository

import com.example.chatapp.app.utils.Resource
import com.google.firebase.auth.AuthResult

interface CreateAccountRepository {
    suspend fun createUser(
        userName: String,
        userLastName: String,
        userEmailAddress: String,
        userLoginPassword: String,
        userImageUri: String,
    ): Resource<AuthResult>
}