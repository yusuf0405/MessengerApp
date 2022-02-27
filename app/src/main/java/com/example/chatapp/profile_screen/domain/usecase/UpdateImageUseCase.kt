package com.example.chatapp.profile_screen.domain.usecase

import android.net.Uri
import android.widget.ImageView
import com.example.chatapp.profile_screen.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateImageUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend fun execute(uri: Uri, imageView: ImageView) =
        repository.updateNameProfile(uri = uri, imageView = imageView)
}