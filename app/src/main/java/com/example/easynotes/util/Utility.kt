package com.example.easynotes.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

object Utility {
    fun getCollectionReference(): CollectionReference {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return FirebaseFirestore
            .getInstance()
            .collection("easy_notes")
            .document(currentUser?.uid!!)
            .collection("notes")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun timeStampToString(timestamp: Timestamp): String {
        return SimpleDateFormat("dd/MM/yyyy").format(timestamp.toDate())
    }
}