package com.example.chatapp.people_screen.domain

import com.example.chatapp.login_screen.domain.models.User

interface PeopleRepository {

    suspend fun allUserList(): List<User>
}