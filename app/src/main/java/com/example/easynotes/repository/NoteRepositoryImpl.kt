package com.example.easynotes.repository

import com.example.easynotes.Result
import com.example.easynotes.data.model.Note
import com.example.easynotes.util.Utility
import com.google.firebase.firestore.Query
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
            val snapShot =
                Utility.getCollectionReference().orderBy("timeStamp", Query.Direction.DESCENDING)
                    .get().await()
            val notes = snapShot.documents.map { document ->
                document.toObject(Note::class.java)?.copy(id = document.id)
            }
            emit(Result.Success(notes))
        } catch (e: Exception) {
            emit(Result.Error(e.stackTraceToString()))
        }
    }

    override suspend fun updateNote(note: Note?): Flow<Result<String>> = flow {
        try {
            val documentReference = Utility.getCollectionReference().document(note?.id!!)
            documentReference.set(note).await()
            emit(Result.Success("Updated successfully"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage ?: "Update failed"))
        }
    }

    override suspend fun deleteNote(noteId: String): Flow<Result<String>> = flow {
        try {
            Utility.getCollectionReference()
                .document(noteId)
                .delete()
                .await()
            emit(Result.Success("Updated successfully"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage ?: "Update failed"))
        }
    }
}