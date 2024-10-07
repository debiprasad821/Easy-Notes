package com.example.easynotes.screens.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.easynotes.R
import com.example.easynotes.ui.theme.LightRed
import com.example.easynotes.ui.theme.Red

@Composable
fun LogoutDialog(
    onClickLogout: () -> Unit,
    onClickCancel: () -> Unit
) {
    Dialog(onDismissRequest = { }) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(70.dp))
                        .background(color = LightRed)
                        .padding(15.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.ic_logout),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Already leaving?",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontSize = 18.sp
                )
                Text(
                    text = "Are you sure you want to logout?",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.weight(0.5f),
                        onClick = {
                            onClickLogout.invoke()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Red,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Yes, Logout",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    OutlinedButton(
                        modifier = Modifier.weight(0.5f),
                        onClick = {
                            onClickCancel.invoke()
                        }
                    ) {
                        Text(
                            text = "Cancel",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(device = Devices.PIXEL)
fun LogoutDialogPreview() {
    LogoutDialog({}, {})
}