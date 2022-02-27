package com.example.chatapp.chat_screen.domain.repository

import com.example.chatapp.chat_screen.domain.models.Message

interface ChatRepository {
    suspend fun addNewMessage(message: Message, receiverRoom: String, senderRoom: String)

    suspend fun allMessageList(senderRoom: String): List<Message>
}