package com.example.chatapp.chat_screen.data

import android.content.ContentValues
import android.util.Log
import com.example.chatapp.app.utils.Cons.Companion.CHATS
import com.example.chatapp.app.utils.Cons.Companion.MESSAGES
import com.example.chatapp.chat_screen.domain.models.Message
import com.example.chatapp.chat_screen.domain.repository.ChatRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay

class ChatRepositoryImpl : ChatRepository {
    private val database = FirebaseDatabase.getInstance().reference
    override suspend fun addNewMessage(message: Message, receiverRoom: String, senderRoom: String) {
        database.child(CHATS).child(senderRoom).child(MESSAGES).push()
            .setValue(message).addOnSuccessListener {
                database.child(CHATS).child(receiverRoom).child(MESSAGES).push()
                    .setValue(message)
            }
    }

    override suspend fun allMessageList(
        senderRoom: String,
    ): List<Message> {
        val messageList = mutableListOf<Message>()
        database.child(CHATS).child(senderRoom).child(MESSAGES)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (singleSnapshot in snapshot.children) {
                        val message = singleSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(ContentValues.TAG, "onCancelled", error.toException())
                }

            })
        delay(2000)
        return messageList
    }
}