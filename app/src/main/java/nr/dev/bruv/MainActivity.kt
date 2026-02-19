package nr.dev.bruv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
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
                        HomeScreen(Modifier.padding(innerPadding), { finish() })
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
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(modifier.fillMaxSize().padding(36.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Image(painter = painterResource(id = R.drawable.esemka), contentDescription = "Esemka Gym Logo",
            modifier = Modifier.padding(horizontal = 30.dp).fillMaxWidth())
        Text("Sign In", fontSize = 4.em, fontWeight = FontWeight.Bold)
        Text("Hi there! Build your muscle with us!", color = Color.Gray)
        Column {
            BoldOrangeText("Email")
            TextField(
                modifier = Modifier.fillMaxWidth().borderBottom(),
                value = email, onValueChange = {str: String -> email = str},
                placeholder = {Text("Email")},
                colors = resetTextField()
            )
        }
        Column {
            BoldOrangeText("Password")
            TextField(
                modifier = Modifier.fillMaxWidth().borderBottom(),
                value = password, onValueChange = {str: String -> password = str},
                placeholder = {Text("Password")},
                colors = resetTextField()
            )
        }
        Button(
            onClick = onLogin,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Sign In", fontWeight = FontWeight.Bold)
        }
        CenterCol {
            Text("or", fontWeight = FontWeight.Bold, color = Color.Gray)
            TextButton(onClick = onRegist, contentPadding = PaddingValues(0.dp)) {
                Text("Sign Up", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun RegisterScreen(modifier: Modifier, onLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isMale by remember { mutableStateOf(true) }
    Column(modifier.fillMaxSize().padding(38.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Sign Up", fontSize = 4.em, fontWeight = FontWeight.Bold)
        Text("Hi there! Register your gym member!", color = Color.Gray)
        Column {
            BoldOrangeText("Email")
            TextField(
                modifier = Modifier.fillMaxWidth().borderBottom(), value = email,
                onValueChange = {str: String -> email = str}, placeholder = {Text("Password")},
                colors = resetTextField()
            )
        }
        Column {
            BoldOrangeText("Password")
            TextField(
                modifier = Modifier.fillMaxWidth().background(Color.Transparent).borderBottom(), value = password,
                onValueChange = {str: String -> password = str},
                colors = resetTextField()
            )
        }
        BoldOrangeText("Gender")
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
fun HomeScreen(modifier: Modifier, onBack: () -> Unit) {
    BackHandler(enabled = true) {
        onBack()
    }
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Home")
    }
}

@Composable
fun CenterCol(function: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        function()
    }
}

@Composable
fun CenterRow(arrangement: Arrangement.Horizontal = Arrangement.Center, function: @Composable () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = arrangement) {
        function()
    }
}

@Composable
fun BoldOrangeText(text: String) {
    Text(text, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
}

fun Modifier.borderBottom(color: Color = Color.LightGray, strokeWidth: Dp = 2.dp): Modifier =
    this.drawBehind {
        val y = size.height - strokeWidth.toPx() / 2
        drawLine(
            color,
            Offset(0f, y),
            Offset(size.width, y),
            strokeWidth.toPx()
        )
    }

@Composable
fun resetTextField(): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledTextColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
    )
}