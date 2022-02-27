package com.example.chatapp.login_screen.data

import com.example.chatapp.app.utils.Resource
import com.example.chatapp.app.utils.safeCall
import com.example.chatapp.login_screen.domain.repository.LoginRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginRepositoryImpl : LoginRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()

    override suspend fun login(email: String, password: String): Resource<AuthResult> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                delay(3000L)
                Resource.Success(result)
            }
        }
    }


}