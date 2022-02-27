package com.example.chatapp.chat_screen.domain.usecase

import com.example.chatapp.chat_screen.domain.repository.ChatRepository
import javax.inject.Inject

class GetAllMessagesUseCase @Inject constructor(private val repository: ChatRepository) {
    suspend fun execute(senderRoom: String) = repository.allMessageList(senderRoom = senderRoom)

}