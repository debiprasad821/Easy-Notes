package com.example.easynotes.repository

import com.example.easynotes.Result
import com.example.easynotes.data.model.Note
import com.example.easynotes.util.Utility
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class NoteRepositoryImpl : NoteRepository {
    override suspend fun addNote(note: Note): Flow<Result<String>> = flow {
        try {
            val documentReference = Utility.getCollectionReference().document()
            documentReference.set(note).await()
            emit(Result.Success("Note added successfully"))
        } catch (e: Exception) {
            emit(Result.Error("Failed to add note"))
        }
    }

    override suspend fun getNotes(): Flow<Result<List<Note?>>> = flow {
        try {
            val snapShot = Utility.getCollectionReference().get().await()
            val notes = snapShot.documents.map { document ->
                document.toObject(Note::class.java)
            }
            emit(Result.Success(notes))
        } catch (e: Exception) {
            emit(Result.Error(e.stackTraceToString()))
        }
    }
}