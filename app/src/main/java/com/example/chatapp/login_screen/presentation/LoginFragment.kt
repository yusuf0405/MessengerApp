package com.example.chatapp.login_screen.presentation

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
import com.example.chatapp.databinding.LoginInFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val binding: LoginInFragmentBinding by lazy(LazyThreadSafetyMode.NONE) {
        LoginInFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.enterBtn.setOnClickListener {
            viewModel.signInUser(
                binding.editLoginLog.text.toString(),
                binding.editPasswordLog.text.toString())
        }
        binding.goToCreateLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginInFragment_to_createAccountFragment)
        }
        viewModel.userSignUpStatus.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressDialog.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressDialog.visibility = View.GONE
                    Toast.makeText(requireContext(), "Logged In Successfully", Toast.LENGTH_SHORT)
                        .show()
                    onBoardingFinished()
                    findNavController().navigate(R.id.action_loginInFragment_to_rootFragment)
                }
                is Resource.Error -> {
                    binding.progressDialog.visibility = View.GONE
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