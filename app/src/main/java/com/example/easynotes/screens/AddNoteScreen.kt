package com.example.easynotes.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.easynotes.R
import com.example.easynotes.Result
import com.example.easynotes.data.model.Note
import com.example.easynotes.viewmodel.NoteViewModel
import com.google.firebase.Timestamp

@Composable
fun AddNoteScreen(
    navController: NavHostController,
    note: Note?,
    noteViewModel: NoteViewModel = hiltViewModel()
) {
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    val noteState by noteViewModel.addNoteState.collectAsState()
    val updateNoteState by noteViewModel.updateNoteState.collectAsState()
    val context = LocalContext.current
    var showLoader by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = note) {
        title = note?.title ?: ""
        description = note?.description ?: ""
    }

    LaunchedEffect(key1 = noteState) {
        when (noteState) {
            is Result.Loading -> {
                showLoader = true
            }
            is Result.Success -> {
                Toast.makeText(
                    context,
                    (noteState as Result.Success).data,
                    Toast.LENGTH_SHORT
                ).show()
                navController.popBackStack()
            }
            is Result.Error -> {
                Toast.makeText(
                    context,
                    (noteState as Result.Error).error,
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {}
        }
    }

    LaunchedEffect(key1 = updateNoteState) {
        when (updateNoteState) {
            is Result.Loading -> {
                showLoader = true
            }
            is Result.Success -> {
                Toast.makeText(
                    context,
                    (updateNoteState as Result.Success).data,
                    Toast.LENGTH_SHORT
                ).show()
                navController.popBackStack()
            }
            is Result.Error -> {
                Toast.makeText(
                    context,
                    (updateNoteState as Result.Error).error,
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopBar(note, navController = navController) {
                if (validate(title, description, context)) {
                    if (note == null) {
                        val noteItem = Note(
                            title = title,
                            description = description,
                            timeStamp = Timestamp.now()
                        )
                        noteViewModel.addNote(noteItem)
                    } else {
                        val noteItem = Note(
                            id = note.id,
                            title = title,
                            description = description,
                            timeStamp = Timestamp.now()
                        )
                        noteViewModel.updateNote(noteItem)
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, top = 20.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                placeholder = {
                    Text(
                        text = "Title",
                        fontFamily = FontFamily(Font(R.font.poppins_regular))
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppins_regular))
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                value = description,
                onValueChange = {
                    description = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                placeholder = {
                    Text(
                        text = "Description",
                        fontFamily = FontFamily(Font(R.font.poppins_regular))
                    )
                },
                singleLine = false,
                maxLines = 7
            )

            if (showLoader) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

fun validate(title: String, description: String, context: Context): Boolean {
    if (title.isEmpty()) {
        Toast.makeText(
            context,
            "Title should not empty",
            Toast.LENGTH_SHORT
        ).show()
        return false
    } else if (description.isEmpty()) {
        Toast.makeText(
            context,
            "Description should not empty",
            Toast.LENGTH_SHORT
        ).show()
        return false
    } else {
        return true
    }
}

@Composable
fun TopBar(note: Note?, navController: NavHostController, onClickAdd: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        title = {
            Text(
                text = if (note == null) "Add New Note" else "Edit Note",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                color = Color.Black
            )
        },
        actions = {
            IconButton(onClick = {
                onClickAdd.invoke()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_tick),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        backgroundColor = Color.White,
        elevation = 0.dp
    )
}

@Composable
@Preview(device = Devices.PIXEL)
fun AddNoteScreenPreview() {
//    AddNoteScreen(navController = rememberNavController())
}