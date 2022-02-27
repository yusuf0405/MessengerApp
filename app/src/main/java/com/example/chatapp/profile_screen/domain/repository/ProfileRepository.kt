package com.example.chatapp.profile_screen.domain.repository

import android.net.Uri
import android.widget.ImageView

interface ProfileRepository {
    suspend fun updateNameProfile(uri: Uri, imageView: ImageView)

    suspend fun updateNameProfile(newName: String)

    suspend fun updateEmailProfile(newEmail: String, password: String)
}