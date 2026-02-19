package nr.dev.bruv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
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
    Column(modifier.fillMaxSize().padding(36.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Image(painter = painterResource(id = R.drawable.esemka), contentDescription = "Esemka Gym Logo",
            modifier = Modifier.padding(horizontal = 30.dp).fillMaxWidth())
        Text("Sign In", fontSize = 4.em, fontWeight = FontWeight.Bold)
        Text("Hi there! Build your muscle with us!", color = Color.Gray)
        OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = username, onValueChange = {str: String -> username = str}, label = {Text("Username")})
        OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = password, onValueChange = {str: String -> password = str}, label = {Text("Password")})
        Button(
            onClick = onLogin,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign In", fontWeight = FontWeight.Bold)
        }
        CenterCol {
            Text("or", fontWeight = FontWeight.Bold, color = Color.Gray)
            TextButton(onClick = onRegist) {
                Text("Sign Up", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun RegisterScreen(modifier: Modifier, onLogin: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isMale by remember { mutableStateOf(true) }
    Column(modifier.fillMaxSize().padding(38.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Sign Up", fontSize = 4.em, fontWeight = FontWeight.Bold)
        Text("Hi there! Register your gym member!", color = Color.Gray)
        OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = username, onValueChange = {str: String -> username = str}, label = {Text("Username")})
        OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = password, onValueChange = {str: String -> password = str}, label = {Text("Password")})
        Text("Gender")
        Column {
            CenterRow(Arrangement.Start) {
                RadioButton(isMale, onClick = {isMale = true})
                Text("Male")
            }
            CenterRow(Arrangement.Start) {
                RadioButton(!isMale, onClick = {isMale = false})
                Text("Female")
            }
        }
        Button(
            onClick = onLogin,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up", fontWeight = FontWeight.Bold)
        }
        CenterRow {
            Text("Have an Account?", fontWeight = FontWeight.Bold, color = Color.Gray)
            TextButton(onClick = onLogin, modifier = Modifier.padding()) {
                Text("Sign In", fontWeight = FontWeight.Bold)
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

@Composable
fun CenterCol(function: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        function()
    }
}

@Composable
fun CenterRow(arrangement: Arrangement.Horizontal = Arrangement.Center, function: @Composable () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = arrangement) {
        function()
    }
}

//fun Modifier.borderBottom(color: Color = Color.Gray, strokeWidth: Dp = 2.dp): Modifier =
//    this.drawBehind {
//        val y = size.height - strokeWidth.toPx() / 2
//
//    }