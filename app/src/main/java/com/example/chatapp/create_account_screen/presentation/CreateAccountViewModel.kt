package com.example.chatapp.create_account_screen.presentation

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.app.utils.Resource
import com.example.chatapp.create_account_screen.domain.usecase.CreateAccountUseCase
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val createAccountUseCase: CreateAccountUseCase,
) : ViewModel() {

    private val _userRegistrationStatus = MutableLiveData<Resource<AuthResult>>()
    val userRegistrationStatus: LiveData<Resource<AuthResult>> = _userRegistrationStatus

    fun createUser(
        userName: String,
        userLastName: String,
        userEmailAddress: String,
        userLoginPassword: String,
        userImageUri: String,
    ) {
        val error =
            if (userEmailAddress.isEmpty() || userName.isEmpty() || userLoginPassword.isEmpty() || userLastName.isEmpty()) {
                "Empty Strings"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmailAddress).matches()) {
                "Not a valid Email"
            } else null

        error?.let {
            _userRegistrationStatus.postValue(Resource.Error(it))
            return
        }
        _userRegistrationStatus.postValue(Resource.Loading())

        viewModelScope.launch(Dispatchers.Main) {
            val registerResult = createAccountUseCase.execute(
                userName = userName,
                userLastName = userLastName,
                userEmailAddress = userEmailAddress,
                userLoginPassword = userLoginPassword,
                userImageUri = userImageUri)
            _userRegistrationStatus.postValue(registerResult)
        }
    }
}