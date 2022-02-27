package com.example.chatapp.people_screen.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.databinding.PeopleItemBinding
import com.example.chatapp.login_screen.domain.models.User
import com.squareup.picasso.Picasso

interface PeopleItemOnClickListener {
    fun showChatScreen(user: User)
}

class PeopleAdapter(private val actionListener: PeopleItemOnClickListener) :
    RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {
    var peopleList: List<User> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()

        }

    inner class PeopleViewHolder(private val binding: PeopleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.apply {
                namePeopleItem.text = user.name
                if (user.imageUri != "") {
                    Picasso.get()
                        .load(user.imageUri)
                        .placeholder(R.drawable.ic_placeholder_no_text)
                        .into(profileImageRef)
                }

            }
            itemView.setOnClickListener {
                actionListener.showChatScreen(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val layoutInflate =
            LayoutInflater.from(parent.context).inflate(R.layout.people_item, parent, false)
        val binding = PeopleItemBinding.bind(layoutInflate)
        return PeopleViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(peopleList[position])
    }

    override fun getItemCount(): Int = peopleList.size
}