package com.example.easynotes.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import androidx.navigation.compose.rememberNavController
import com.example.easynotes.R
import com.example.easynotes.Result
import com.example.easynotes.data.model.Note
import com.example.easynotes.viewmodel.NoteViewModel
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch

@Composable
fun AddNoteScreen(
    navController: NavHostController,
    noteViewModel: NoteViewModel = hiltViewModel()
) {
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    val noteState by noteViewModel.addNoteState.collectAsState()
    val context = LocalContext.current
    var showLoader by remember {
        mutableStateOf(false)
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

    Scaffold(
        topBar = {
            TopBar {
                val note = Note(
                    title = title,
                    description = description,
                    timeStamp = Timestamp.now()
                )
                noteViewModel.addNote(note)
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

@Composable
fun TopBar(onClickAdd: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Add New Note",
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
    AddNoteScreen(navController = rememberNavController())
}