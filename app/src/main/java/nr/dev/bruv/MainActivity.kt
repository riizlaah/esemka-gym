package nr.dev.bruv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nr.dev.bruv.ui.theme.BruvTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BruvTheme {
                var isLogin by remember { mutableStateOf(true) }
                var isRegister by remember { mutableStateOf(false) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if(isLogin && !isRegister) {
                        LoginScreen(Modifier.padding(innerPadding), {isLogin = false;isRegister = false}, {isRegister = true; isLogin = false})
                    } else if(!isLogin && !isRegister) {
                        HomeScreen(Modifier.padding(innerPadding))
                    } else {
                        RegisterScreen(Modifier.padding(innerPadding), onLogin = {isLogin = true;isRegister = false})
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(modifier: Modifier, onLogin: () -> Unit, onRegist: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(modifier.fillMaxSize().padding(16.dp)) {
        Text("Sign In")
        OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = username, onValueChange = {str: String -> username = str}, label = {Text("Username")})
        OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = password, onValueChange = {str: String -> password = str}, label = {Text("Password")})
        Button(
            onClick = onLogin,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign In")
        }
        Text("Or")
        TextButton(onClick = onRegist) {
            Text("Sign Up")
        }
    }
}

@Composable
fun RegisterScreen(modifier: Modifier, onLogin: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isMale by remember { mutableStateOf(true) }
    Column(modifier.fillMaxSize().padding(16.dp)) {
        Text("Sign Up")
        OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = username, onValueChange = {str: String -> username = str}, label = {Text("Username")})
        OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = password, onValueChange = {str: String -> password = str}, label = {Text("Password")})
        Text("Gender")
        Row {
            RadioButton(isMale, onClick = {isMale = true})
            Text("Male")
        }
        Row {
            RadioButton(!isMale, onClick = {isMale = false})
            Text("Female")
        }
        Button(
            onClick = onLogin,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }
        Row {
            Text("Or")
            TextButton(onClick = onLogin) {
                Text("Sign In")
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Home")
    }
}