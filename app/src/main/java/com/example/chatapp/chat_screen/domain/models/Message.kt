package com.example.chatapp.chat_screen.domain.models

class Message {
    var message: String? = null
    var senderId: String? = null

    constructor() {}

    constructor(message: String?, senderId: String?){
        this.message = message
        this.senderId = senderId
    }
}