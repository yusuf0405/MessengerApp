package com.example.chatapp.app.di

import com.example.chatapp.chat_screen.data.ChatRepositoryImpl
import com.example.chatapp.chat_screen.domain.repository.ChatRepository
import com.example.chatapp.create_account_screen.data.CreateAccountRepositoryImpl
import com.example.chatapp.create_account_screen.domain.repository.CreateAccountRepository
import com.example.chatapp.login_screen.data.LoginRepositoryImpl
import com.example.chatapp.login_screen.domain.repository.LoginRepository
import com.example.chatapp.people_screen.data.PeopleRepositoryImpl
import com.example.chatapp.people_screen.domain.PeopleRepository
import com.example.chatapp.profile_screen.data.ProfileRepositoryImpl
import com.example.chatapp.profile_screen.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideLoginRepository(): LoginRepository = LoginRepositoryImpl()

    @Provides
    @Singleton
    fun provideCreateAccountRepository(): CreateAccountRepository = CreateAccountRepositoryImpl()

    @Provides
    @Singleton
    fun providePeopleRepository(): PeopleRepository = PeopleRepositoryImpl()


    @Provides
    @Singleton
    fun provideChatRepository(): ChatRepository = ChatRepositoryImpl()

    @Provides
    @Singleton
    fun provideProfileRepository(): ProfileRepository = ProfileRepositoryImpl()


}