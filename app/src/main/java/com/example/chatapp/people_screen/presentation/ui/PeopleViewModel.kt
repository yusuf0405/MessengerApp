package com.example.chatapp.people_screen.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.login_screen.domain.models.User
import com.example.chatapp.people_screen.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
) : ViewModel() {
    private val _usersList: MutableLiveData<List<User>> = MutableLiveData()
    val usersList: LiveData<List<User>> = _usersList

    init {
        allUsers()
    }

    fun allUsers() {
        viewModelScope.launch(Dispatchers.Main) {
            _usersList.value = getAllUsersUseCase.execute()
        }
    }
}