package com.example.chatapp.profile_screen.presentation

import android.net.Uri
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.profile_screen.domain.usecase.UpdateEmailUseCase
import com.example.chatapp.profile_screen.domain.usecase.UpdateImageUseCase
import com.example.chatapp.profile_screen.domain.usecase.UpdateNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val updateImageUseCase: UpdateImageUseCase,
    private val updateNameUseCase: UpdateNameUseCase,
    private val updateEmailUseCase: UpdateEmailUseCase,
) : ViewModel() {

    fun updateImage(uri: Uri, imageView: ImageView) {
        viewModelScope.launch {
            updateImageUseCase.execute(uri = uri, imageView = imageView)
        }
    }

    fun updateName(newName: String) {
        viewModelScope.launch {
            updateNameUseCase.execute(newName = newName)
        }
    }

    fun updateEmail(newEmail: String, password: String) {
        viewModelScope.launch {
            updateEmailUseCase.execute(newEmail = newEmail, password = password)
        }
    }
}