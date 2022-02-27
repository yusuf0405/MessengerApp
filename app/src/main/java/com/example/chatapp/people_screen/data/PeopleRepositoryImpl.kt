package com.example.chatapp.people_screen.data

import android.content.ContentValues
import android.util.Log
import com.example.chatapp.app.utils.Cons.Companion.USERS
import com.example.chatapp.login_screen.domain.models.User
import com.example.chatapp.people_screen.domain.PeopleRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay

class PeopleRepositoryImpl : PeopleRepository {
    private var fireBase = FirebaseDatabase.getInstance()
    private var userRef = fireBase.getReference(USERS)

    override suspend fun allUserList(): List<User> {
        val list = mutableListOf<User>()
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (singleSnapshot in snapshot.children) {
                    val user = singleSnapshot.getValue(User::class.java)
                    if (user?.id != FirebaseAuth.getInstance().currentUser?.uid) {
                        list.add(user!!)

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(ContentValues.TAG, "onCancelled", error.toException())
            }

        })
        delay(4000)
        return list

    }
}
