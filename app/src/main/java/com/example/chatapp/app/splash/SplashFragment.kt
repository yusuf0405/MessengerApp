package com.example.chatapp.app.splash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentSplashBinding
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class SplashFragment : Fragment() {

    private val binding: FragmentSplashBinding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentSplashBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        GlobalScope.launch(Dispatchers.Main) {
            delay(2000)
            if (onBoardingFinished()) {
                findNavController().navigate(R.id.action_splashFragment_to_rootFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_loginInFragment)
            }
        }
        return binding.root
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

}