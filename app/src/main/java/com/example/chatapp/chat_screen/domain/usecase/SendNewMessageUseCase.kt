package com.example.chatapp.chat_screen.domain.usecase

import com.example.chatapp.chat_screen.domain.models.Message
import com.example.chatapp.chat_screen.domain.repository.ChatRepository
import javax.inject.Inject

class SendNewMessageUseCase @Inject constructor(private val repository: ChatRepository) {
    suspend fun execute(message: Message, receiverRoom: String, senderRoom: String) =
        repository.addNewMessage(
            message = message,
            receiverRoom = receiverRoom,
            senderRoom = senderRoom)
}