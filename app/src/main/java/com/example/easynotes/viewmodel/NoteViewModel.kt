package com.example.easynotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easynotes.Result
import com.example.easynotes.data.model.Note
import com.example.easynotes.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {
    private val _addNoteState: MutableStateFlow<Result<String>?> = MutableStateFlow(null)
    var addNoteState = _addNoteState

    private val _updateNoteState: MutableStateFlow<Result<String>?> = MutableStateFlow(null)
    var updateNoteState = _updateNoteState

    private val _notes: MutableStateFlow<Result<List<Note?>>?> = MutableStateFlow(null)
    var notes = _notes

    fun addNote(note: Note) {
        viewModelScope.launch {
            _addNoteState.value = Result.Loading
            noteRepository.addNote(note).collect {
                _addNoteState.value = it
            }
        }
    }

    fun getAllNotes() {
        viewModelScope.launch {
            notes.value = Result.Loading
            noteRepository.getNotes().collect {
                _notes.value = it
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            _updateNoteState.value = Result.Loading
            noteRepository.updateNote(note).collect {
                _updateNoteState.value = it
            }
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            noteRepository.deleteNote(noteId).collect {
                when (it) {
                    is Result.Success -> {
                        getAllNotes()
                    }
                    else -> {}
                }
            }
        }
    }
}