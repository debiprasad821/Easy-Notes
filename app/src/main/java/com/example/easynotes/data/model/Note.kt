package com.example.easynotes.data.model

import com.google.firebase.Timestamp
import java.io.Serializable

data class Note(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val timeStamp: Timestamp = Timestamp.now()
) : Serializable
