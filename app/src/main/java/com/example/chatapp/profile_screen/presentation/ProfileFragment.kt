package com.example.chatapp.profile_screen.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.chatapp.R
import com.example.chatapp.databinding.ProfileFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {


    private val binding: ProfileFragmentBinding by lazy(LazyThreadSafetyMode.NONE) {
        ProfileFragmentBinding.inflate(layoutInflater)
    }

    private val viewModel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }


    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            txtEmailProfile.text = FirebaseAuth.getInstance().currentUser?.email
            txtNameProfile.text = FirebaseAuth.getInstance().currentUser?.displayName


            Picasso.get()
                .load(FirebaseAuth.getInstance().currentUser?.photoUrl)
                .placeholder(R.drawable.ic_placeholder_no_text)
                .into(binding.profileImageRef)

            refactorImageUser.setOnClickListener {
                getImage()
            }

            binding.refactorNameBtn.setOnClickListener {
                val dialog = BottomSheetDialog(requireContext())
                val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_refactor_name, null)
                dialog.setCancelable(false)
                dialog.setContentView(dialogView)
                dialog.show()

                val btnClose = dialogView.findViewById<TextView>(R.id.cancel_btn)
                val btnSave = dialogView.findViewById<TextView>(R.id.save_btn)
                val nameEditText = dialogView.findViewById<EditText>(R.id.refactor_name_txt)
                nameEditText.setText(FirebaseAuth.getInstance().currentUser?.displayName)

                btnClose.setOnClickListener {
                    dialog.dismiss()
                }
                btnSave.setOnClickListener {
                    val newName = nameEditText.text.toString()
                    viewModel.updateName(newName = newName)
                    binding.txtNameProfile.text = newName
                    dialog.dismiss()
                }
            }
            binding.refactorEmailBtn.setOnClickListener {
                val dialog = BottomSheetDialog(requireContext())
                val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_refactor_email, null)
                dialog.setCancelable(false)
                dialog.setContentView(dialogView)
                dialog.show()

                val btnClose = dialogView.findViewById<TextView>(R.id.cancel_btn)
                val btnSave = dialogView.findViewById<TextView>(R.id.save_btn)
                val emailEditText = dialogView.findViewById<EditText>(R.id.refactor_email_txt)
                val passwordText = dialogView.findViewById<EditText>(R.id.password_txt)
                emailEditText.setText(FirebaseAuth.getInstance().currentUser?.email)

                btnClose.setOnClickListener {
                    dialog.dismiss()
                }
                btnSave.setOnClickListener {
                    val newEmail = emailEditText.text.toString()
                    val password = passwordText.text.toString()
                    viewModel.updateEmail(newEmail = newEmail, password = password)
                    dialog.dismiss()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && data != null && data.data != null) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.updateImage(uri = data.data!!, imageView = binding.profileImageRef)
            }

        }
    }


    private fun getImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 1)

    }
}