package com.example.chatapp.people_screen.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.app.utils.Cons.Companion.USER_KEY
import com.example.chatapp.chat_screen.presentation.ui.ChatActivity
import com.example.chatapp.databinding.PeopleFragmentBinding
import com.example.chatapp.login_screen.domain.models.User
import com.example.chatapp.people_screen.presentation.adapter.PeopleAdapter
import com.example.chatapp.people_screen.presentation.adapter.PeopleItemOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeopleFragment : Fragment() {

    private val binding: PeopleFragmentBinding by lazy(LazyThreadSafetyMode.NONE) {
        PeopleFragmentBinding.inflate(layoutInflater)
    }

    private val viewModel: PeopleViewModel by viewModels()
    private val adapter = PeopleAdapter(object : PeopleItemOnClickListener {
        override fun showChatScreen(user: User) {
            val intent = Intent(requireContext(), ChatActivity::class.java)
            intent.putExtra(USER_KEY, user)
            requireContext().startActivity(intent)
        }


    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.usersList.observe(viewLifecycleOwner) {
            adapter.peopleList = it
        }

    }
}