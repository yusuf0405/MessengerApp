package com.example.chatapp.chat_screen.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.app.utils.Cons.Companion.USER_KEY
import com.example.chatapp.chat_screen.domain.models.Message
import com.example.chatapp.chat_screen.presentation.adapter.MessageAdapter
import com.example.chatapp.databinding.ActivityChatBinding
import com.example.chatapp.login_screen.domain.models.User
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {

    private val binding: ActivityChatBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityChatBinding.inflate(layoutInflater)
    }
    private val viewModel: ChatViewModel by viewModels()
    private val adapter = MessageAdapter()
    private var messageList = mutableListOf<Message>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.chatRecycler.layoutManager = LinearLayoutManager(this)
        binding.chatRecycler.adapter = adapter

        val user = intent.extras?.getSerializable(USER_KEY) as User
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        val receiverUid = user.id
        val senderRoom = receiverUid + senderUid
        val receiverRoom = senderUid + receiverUid

        supportActionBar?.title = user.name
        viewModel.getAllMessages(senderRoom = senderRoom)

        viewModel.messageList.observe(this) {
            messageList = it.toMutableList()
            adapter.messageList = messageList
        }
        binding.sendButton.setOnClickListener {
            val messageText = binding.messageBox.text.toString()
            val message = Message(messageText, FirebaseAuth.getInstance().currentUser?.uid)
            messageList.add(message)
            adapter.notifyDataSetChanged()
            if (messageText.isNotEmpty()) {
                viewModel.sendNewMessage(
                    message = message,
                    senderRoom = senderRoom,
                    receiverRoom = receiverRoom
                )
                binding.messageBox.setText("")
            }
        }
    }
}
