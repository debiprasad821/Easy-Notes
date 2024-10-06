package com.example.easynotes.repository

import com.example.easynotes.Result
import com.example.easynotes.data.model.Note
import kotlinx.coroutines.flow.Flow


interface NoteRepository {
    suspend fun addNote(note: Note): Flow<Result<String>>
    suspend fun getNotes(): Flow<Result<List<Note?>>>
}