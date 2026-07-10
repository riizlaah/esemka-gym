package com.example.esemkagym

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esemkagym.ui.theme.EsemkaGymTheme
import com.example.esemkagym.ui.theme.red0

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EsemkaGymTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(24.dp)
                    ) {
                        var email by remember { mutableStateOf("") }
                        var password by remember { mutableStateOf("") }
                        var showPassword by remember { mutableStateOf(false) }
                        var errMsg by remember { mutableStateOf("") }
                        var loading by remember { mutableStateOf(false) }
                        val scope = rememberCoroutineScope()
                        val ctx = LocalContext.current


                        Image(
                            painterResource(R.drawable.logo),
                            "Logo",
                            Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        )
                        Text(
                            "Esemka Gym",
                            fontWeight = FontWeight.Bold,
                            fontSize = typ().headlineLarge.fontSize
                        )
                        Text("Hi there! Build your muscle with us!", color = Color.Gray)
                        Spacer(Modifier.height(24.dp))
                        Text("Email", color = red0)
                        TextField(
                            email,
                            { email = it },
                            Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White,
                            ),
                            singleLine = true
                        )
                        Spacer(Modifier.height(12.dp))
                        Text("Password", color = red0)
                        TextField(
                            password,
                            { password = it },
                            Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White,
                            ),
                            singleLine = true,
                            visualTransformation = if(showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                            suffix = {
                                Icon(painterResource(if(showPassword) R.drawable.visibility else R.drawable.visibility_off), "Toggle Password", Modifier.clickable {
                                    showPassword = !showPassword
                                })
                            }
                        )
                        Spacer(Modifier.height(12.dp))
                        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            ErrText(errMsg, Modifier.fillMaxWidth())
                            Button({

                            }, Modifier.fillMaxWidth(), shape = corner()) {
                                Text("Sign In", fontWeight = FontWeight.Bold)
                            }
                            Text("or", Modifier.padding(vertical = 12.dp), color = Color.Gray)
                            TextButton({}, ) { Text("Sign Up", fontWeight = FontWeight.Bold) }
                        }
                    }
                }
            }
        }
    }
}
