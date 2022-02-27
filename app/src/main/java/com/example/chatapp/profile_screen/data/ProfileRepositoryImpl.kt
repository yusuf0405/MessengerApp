package com.example.chatapp.profile_screen.data

import android.net.Uri
import android.widget.ImageView
import com.example.chatapp.app.utils.Cons.Companion.IMAGE_USER
import com.example.chatapp.app.utils.Cons.Companion.USERS
import com.example.chatapp.profile_screen.domain.repository.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class ProfileRepositoryImpl : ProfileRepository {
    private val database = FirebaseStorage.getInstance().getReference(IMAGE_USER)

    private var fireBase = Firebase.database
    private var myRef =
        fireBase.getReference(USERS)
    private val currentUser = FirebaseAuth.getInstance().currentUser


    override suspend fun updateNameProfile(uri: Uri, imageView: ImageView) {
        val storageReference = database.child(FirebaseAuth.getInstance().currentUser!!.uid)
        storageReference.putFile(uri).addOnFailureListener {
        }.addOnSuccessListener() {
            storageReference.downloadUrl.addOnCompleteListener() { taskSnapshot ->
                val url = taskSnapshot.result
                myRef.child(currentUser!!.uid + "/" + "imageUri").setValue(url.toString())
                currentUser.updateProfile(userProfileChangeRequest {
                    photoUri = url
                })
                Picasso.get()
                    .load(url)
                    .into(imageView)

            }
        }
    }

    override suspend fun updateNameProfile(newName: String) {
        myRef.child(FirebaseAuth.getInstance().currentUser!!.uid + "/" + "name").setValue(newName)
        FirebaseAuth.getInstance().currentUser!!.updateProfile(userProfileChangeRequest {
            displayName = newName
        })
    }

    override suspend fun updateEmailProfile(newEmail: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(currentUser!!.email!!,
            password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                currentUser.updateEmail(newEmail)
                myRef.child(FirebaseAuth.getInstance().currentUser!!.uid + "/" + "email")
                    .setValue(newEmail)
            }

        }
    }
}