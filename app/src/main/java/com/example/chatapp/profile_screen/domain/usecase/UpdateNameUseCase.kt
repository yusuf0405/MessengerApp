package com.example.chatapp.profile_screen.domain.usecase

import com.example.chatapp.profile_screen.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateNameUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend fun execute(newName: String) = repository.updateNameProfile(newName = newName)
}