package com.example.chatapp.people_screen.domain.usecase

import com.example.chatapp.login_screen.domain.models.User
import com.example.chatapp.people_screen.domain.PeopleRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(private val repository: PeopleRepository) {

    suspend fun execute(): List<User> = repository.allUserList()
}