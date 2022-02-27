package com.example.chatapp.create_account_screen.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.chatapp.R
import com.example.chatapp.app.utils.Resource
import com.example.chatapp.databinding.CreateAccountFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountFragment : Fragment() {

    private val binding: CreateAccountFragmentBinding by lazy(LazyThreadSafetyMode.NONE) {
        CreateAccountFragmentBinding.inflate(layoutInflater)
    }

    private val viewModel: CreateAccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            viewModel.createUser(
                userName = binding.editNameCreate.text.toString(),
                userLastName = binding.editLastnameCreate.text.toString(),
                userEmailAddress = binding.editLoginCreate.text.toString(),
                userLoginPassword = binding.editPasswordCreate.text.toString(),
                userImageUri = "")
        }
        binding.goToLoginFragment.setOnClickListener {
            findNavController().navigate(R.id.action_createAccountFragment_to_loginInFragment)
        }

        viewModel.userRegistrationStatus.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(),
                        "Registered Successfully",
                        Toast.LENGTH_SHORT).show()
                    onBoardingFinished()
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_createAccountFragment_to_rootFragment)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}