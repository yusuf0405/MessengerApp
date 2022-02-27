package com.example.chatapp.login_screen.domain.models

import java.io.Serializable

data class User(
    var id: String = "",
    var name: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = "",
    var imageUri: String = "",
) : Serializable
