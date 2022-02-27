package com.example.chatapp.profile_screen.domain.usecase

import com.example.chatapp.profile_screen.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateEmailUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend fun execute(newEmail: String, password: String) =
        repository.updateEmailProfile(newEmail = newEmail, password = password)
}