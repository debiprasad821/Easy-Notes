package com.example.easynotes.di

import com.example.easynotes.repository.AuthRepository
import com.example.easynotes.repository.AuthRepositoryImpl
import com.example.easynotes.repository.NoteRepository
import com.example.easynotes.repository.NoteRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NoteModule {
    @Provides
    @Singleton
    fun provideNoteRepository(): NoteRepository {
        return NoteRepositoryImpl()
    }
}