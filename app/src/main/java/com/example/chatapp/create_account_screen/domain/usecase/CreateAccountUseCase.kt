package com.example.chatapp.create_account_screen.domain.usecase

import com.example.chatapp.create_account_screen.domain.repository.CreateAccountRepository
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(private val repository: CreateAccountRepository) {
    suspend fun execute(
        userName: String,
        userLastName: String,
        userEmailAddress: String,
        userLoginPassword: String,
        userImageUri: String,
    ) = repository.createUser(userName = userName,
        userLastName = userLastName,
        userEmailAddress = userEmailAddress,
        userLoginPassword = userLoginPassword,
        userImageUri = userImageUri
    )

}