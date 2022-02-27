package com.example.chatapp.create_account_screen.data

import com.example.chatapp.app.utils.Cons.Companion.USERS
import com.example.chatapp.app.utils.Resource
import com.example.chatapp.app.utils.safeCall
import com.example.chatapp.create_account_screen.domain.repository.CreateAccountRepository
import com.example.chatapp.login_screen.domain.models.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class CreateAccountRepositoryImpl : CreateAccountRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference = FirebaseDatabase.getInstance().getReference(USERS)

    override suspend fun createUser(
        userName: String,
        userLastName: String,
        userEmailAddress: String,
        userLoginPassword: String,
        userImageUri: String,
    ): Resource<AuthResult> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val registrationResult =
                    firebaseAuth.createUserWithEmailAndPassword(userEmailAddress, userLoginPassword)
                        .await()


                val userId = registrationResult.user?.uid!!
                val newUser =
                    User(
                        id = userId,
                        name = userName,
                        lastName = userLastName,
                        email = userEmailAddress,
                        password = userLoginPassword,
                        imageUri = userImageUri)

                firebaseAuth.currentUser!!.updateProfile(userProfileChangeRequest {
                    displayName = newUser.name
                })

                databaseReference.child(userId).setValue(newUser).await()
                Resource.Success(registrationResult)
            }
        }
    }
}