package com.example.easynotes.screens

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
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
import com.example.easynotes.AuthState
import com.example.easynotes.R
import com.example.easynotes.Result
import com.example.easynotes.data.model.Note
import com.example.easynotes.ui.theme.ButtonColor
import com.example.easynotes.ui.theme.LightGray
import com.example.easynotes.util.Utility
import com.example.easynotes.viewmodel.AuthViewModel
import com.example.easynotes.viewmodel.NoteViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun HomeScreen(
    navController: NavHostController,
    noteViewModel: NoteViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val firebaseAuth = FirebaseAuth.getInstance()
    val displayName = firebaseAuth.currentUser?.displayName ?: "Guest"
    val notesState by noteViewModel.notes.collectAsState()
    val authState by authViewModel.authState.collectAsState()
    val context = LocalContext.current
    var query by remember {
        mutableStateOf("")
    }
    var notes by remember {
        mutableStateOf<List<Note?>>(emptyList())
    }
    var loading by remember {
        mutableStateOf(false)
    }
    var menuDropdownExpanded by remember {
        mutableStateOf(false)
    }
    var showMoreDropdown by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = authState) {
        when (authState) {
            is AuthState.Success -> {
                authViewModel.saveIsLoggedIn(false)
                navController.navigate("signIn") {
                    popUpTo("home") {
                        inclusive = true
                    }
                }
            }
            is AuthState.Error -> {
                Toast.makeText(
                    context,
                    (authState as AuthState.Error).error,
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {}
        }
    }

    LaunchedEffect(key1 = Unit) {
        noteViewModel.getAllNotes()
    }

    LaunchedEffect(key1 = notesState) {
        when (notesState) {
            is Result.Success -> {
                loading = false
                notes = (notesState as Result.Success).data
            }
            is Result.Error -> {
                loading = false
                Log.e("Api Error", (notesState as Result.Error).error)
            }
            is Result.Loading -> {
                loading = true
            }
            else -> {

            }
        }
    }

    val filteredNotes = notes.filter { note ->
        note?.title?.contains(query, true) == true ||
                note?.description?.contains(query, true) == true
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(
                    name = displayName,
                    menuDropdownExpanded = menuDropdownExpanded,
                    authViewModel = authViewModel,
                    onClickMenu = {
                        menuDropdownExpanded = true
                    },
                    onDismiss = {
                        menuDropdownExpanded = false
                    }
                )
            },
            floatingActionButton = {
                FloatingButton {
                    navController.navigate("add_note")
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                SearchBar(
                    query,
                    onValueChange = {
                        query = it
                    }
                )

                // Notes List
                if (loading) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    if (filteredNotes.isNotEmpty()) {
                        LazyColumn(
                            contentPadding = PaddingValues(vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(filteredNotes) { note ->
                                NoteItem(
                                    note = note,
                                    onClickUpdate = { note ->
                                        val noteJson = Gson().toJson(note)
                                        navController.navigate(
                                            "add_note?note=$noteJson"
                                        )
                                    },
                                    onClickDelete = {
                                        noteViewModel.deleteNote(it)
                                    }
                                )
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "No Notes Found",
                                fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun NoteItem(
    note: Note?,
    onClickUpdate: (note: Note?) -> Unit,
    onClickDelete: (noteId: String) -> Unit
) {
    var showMoreDropdown by remember {
        mutableStateOf(false)
    }
    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(7.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                ) {
                    Text(
                        text = note?.title ?: "-",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
                    )
                    Text(
                        text = note?.description ?: "-",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular))
                    )
                }

                Box {
                    IconButton(
                        onClick = {
                            showMoreDropdown = true
                        },
                        modifier = Modifier
                            .size(22.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null
                        )
                    }

                    DropdownMenu(
                        expanded = showMoreDropdown,
                        onDismissRequest = {
                            showMoreDropdown = false
                        }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                onClickUpdate.invoke(note)
                                showMoreDropdown = false
                            }
                        ) {
                            Text(text = "Update")
                        }
                        DropdownMenuItem(
                            onClick = {
                                onClickDelete.invoke(note!!.id)
                                showMoreDropdown = false
                            }
                        ) {
                            Text(text = "Delete")
                        }
                    }
                }
            }

            Text(
                text = Utility.timeStampToString(note!!.timeStamp),
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 5.dp, end = 8.dp)
                    .align(Alignment.End),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun SearchBar(query: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 7.dp),
        value = query,
        onValueChange = {
            onValueChange.invoke(it)
        },
        placeholder = {
            Text(
                text = "Search Note",
                fontFamily = FontFamily(Font(R.font.poppins_regular))
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        },
        textStyle = TextStyle(
            fontFamily = FontFamily(Font(R.font.poppins_regular))
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            backgroundColor = LightGray
        ),
        shape = RoundedCornerShape(7.dp),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun FloatingButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = {
            onClick.invoke()
        },
        backgroundColor = ButtonColor,
        contentColor = Color.White
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
fun TopBar(
    name: String,
    menuDropdownExpanded: Boolean,
    authViewModel: AuthViewModel,
    onClickMenu: () -> Unit,
    onDismiss: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Hii, $name",
                fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                fontSize = 20.sp
            )
        },
        backgroundColor = Color.White,
        contentColor = Color.Black,
        elevation = 0.dp,
        actions = {
            Box {
                IconButton(
                    onClick = {
                        onClickMenu.invoke()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null
                    )
                }

                DropdownMenu(
                    expanded = menuDropdownExpanded,
                    onDismissRequest = {
                        onDismiss.invoke()
                    }
                ) {
                    DropdownMenuItem(onClick = {
                        authViewModel.signOut()
                        onDismiss.invoke()
                    }) {
                        Text(text = "Logout")
                    }
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
@Preview(device = Devices.PIXEL)
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
@Preview
fun NoteItemPreview() {
//    NoteItem(note = Note())
}