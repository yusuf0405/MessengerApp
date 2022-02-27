package com.example.chatapp.app.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentRootBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class RootFragment : Fragment() {
    //Создаем ViewBinding
    private val binding: FragmentRootBinding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentRootBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tableLayout.tabIconTint = null
        binding.viewPager2.adapter =
            ViewPagerAdapter(fm = requireActivity().supportFragmentManager, lifecycle = lifecycle)
        TabLayoutMediator(binding.tableLayout, binding.viewPager2) { tab, pos ->
            when (pos) {
                0 -> {
                    tab.setText(R.string.people_title)
                }
                1 -> {
                    tab.setText(R.string.profile_title)
                }

            }
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.login_out_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.login_out) {
            FirebaseAuth.getInstance().signOut()
            onBoardingFinished()
            findNavController().navigate(R.id.action_rootFragment_to_splashFragment)
            return true
        }
        return true

    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", false)
        editor.apply()
    }
}