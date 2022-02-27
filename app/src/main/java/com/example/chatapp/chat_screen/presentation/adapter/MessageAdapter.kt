package com.example.chatapp.chat_screen.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.chat_screen.domain.models.Message
import com.example.chatapp.databinding.ReceiveItemBinding
import com.example.chatapp.databinding.SentItemBinding
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var messageList: List<Message> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    inner class SentViewHolder(private val binding: SentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.txtSentMessage.text = message.message
        }
    }

    inner class ReceiveViewHolder(private val binding: ReceiveItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.txtReceiveMessage.text = message.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == ITEM_RECEIVE) {
            val sentView = layoutInflater.inflate(R.layout.receive_item, parent, false)
            val binding = ReceiveItemBinding.bind(sentView)
            ReceiveViewHolder(binding)
        } else {
            val receiveView = layoutInflater.inflate(R.layout.sent_item, parent, false)
            val binding = SentItemBinding.bind(receiveView)
            SentViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.javaClass == SentViewHolder::class.java) {
            val viewHolder = holder as SentViewHolder
            viewHolder.bind(messageList[position])
        } else {
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.bind(messageList[position])

        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        return if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            ITEM_SENT
        } else {
            ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int = messageList.size

    companion object {
        private const val ITEM_RECEIVE = 1
        private const val ITEM_SENT = 2
    }
}