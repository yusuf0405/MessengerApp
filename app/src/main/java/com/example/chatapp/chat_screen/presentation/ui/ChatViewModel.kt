package com.example.chatapp.chat_screen.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.chat_screen.domain.models.Message
import com.example.chatapp.chat_screen.domain.usecase.GetAllMessagesUseCase
import com.example.chatapp.chat_screen.domain.usecase.SendNewMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendNewMessageUseCase: SendNewMessageUseCase,
    private val getAllMessagesUseCase: GetAllMessagesUseCase,
) : ViewModel() {

    private val _messageList: MutableLiveData<List<Message>> = MutableLiveData()
    val messageList: LiveData<List<Message>> = _messageList

    fun sendNewMessage(message: Message, receiverRoom: String, senderRoom: String) {
        viewModelScope.launch {
            sendNewMessageUseCase.execute(
                message = message,
                receiverRoom = receiverRoom,
                senderRoom = senderRoom
            )
        }
    }

    fun getAllMessages(senderRoom: String) {
        viewModelScope.launch {
            _messageList.value = getAllMessagesUseCase.execute(senderRoom = senderRoom)
        }
    }
}