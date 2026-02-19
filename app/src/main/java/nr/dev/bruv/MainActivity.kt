package nr.dev.bruv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nr.dev.bruv.ui.theme.BruvTheme


class MainActivity : ComponentActivity() {
    lateinit var navCtrl: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BruvTheme {
                navCtrl = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                    NavHost(
                        navCtrl,
                        startDestination = Screen.Login.route
                    ) {
                        composable(Screen.Login.route) {
                            LoginScreen(modifier, navCtrl)
                        }
                        composable(Screen.SignUp.route) {
                            SignUpScreen(modifier, navCtrl)
                        }
                        composable(Screen.Home.route) {
                            HomeScreen(modifier, {finish()})
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(modifier: Modifier, controller: NavHostController) {
    var email by remember { mutableStateOf("") }
    val passwordState = remember { TextFieldState() }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Column(
        modifier
            .fillMaxSize()
            .padding(36.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.esemka),
            contentDescription = "Esemka Gym Logo",
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
        )
        Text("Sign In", fontSize = 4.em, fontWeight = FontWeight.Bold)
        Text("Hi there! Build your muscle with us!", color = Color.Gray)
        Column {
            BoldOrangeText("Email")
            BasicTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .borderBottom()
                    .padding(start = 8.dp, top = 12.dp, end = 8.dp, bottom = 8.dp),
                singleLine = true
            )
        }
        Column {
            BoldOrangeText("Password")
            PasswordField(passwordState)
        }
        Button(
            onClick = {
                scope.launch {
                    loading = true
                    delay(1000)
                    loading = false
                    controller.navigate(Screen.Home.route)
                }
            },
            enabled = !loading,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    color = Color.White
                )
                Spacer(Modifier.width(8.dp))
            }
            Text("Sign In", fontWeight = FontWeight.Bold)
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("or", fontWeight = FontWeight.Bold, color = Color.Gray)
            TextButton(
                onClick = { controller.navigate(Screen.SignUp.route) },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("Sign Up", fontSize = 2.5.em, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SignUpScreen(modifier: Modifier, controller: NavHostController) {
    var email by remember { mutableStateOf("") }
    val passwordState = remember { TextFieldState() }
    var isMale by remember { mutableStateOf(true) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Column(
        modifier
            .fillMaxSize()
            .padding(38.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Sign Up", fontSize = 4.em, fontWeight = FontWeight.Bold)
        Text("Hi there! Register your gym member!", color = Color.Gray)
        Column {
            BoldOrangeText("Email")
            BasicTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .borderBottom()
                    .padding(start = 8.dp, top = 12.dp, end = 8.dp, bottom = 8.dp),
                singleLine = true
            )
        }
        Column {
            BoldOrangeText("Password")
            PasswordField(passwordState)
        }
        BoldOrangeText("Gender")
        Column {
            CenterRow(Arrangement.Start) {
                RadioButton(isMale, onClick = { isMale = true })
                Text("Male")
            }
            CenterRow(Arrangement.Start) {
                RadioButton(!isMale, onClick = { isMale = false })
                Text("Female")
            }
        }
        Button(
            onClick = {
                scope.launch {
                    loading = true
                    delay(1000)
                    loading = false
                    controller.navigate(Screen.Login.route)
                }
            },
            enabled = !loading,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    color = Color.White
                )
                Spacer(Modifier.width(8.dp))
            }
            Text("Sign Up", fontWeight = FontWeight.Bold)
        }
        CenterRow {
            Text("Have an Account?", fontWeight = FontWeight.Bold, color = Color.Gray)
            TextButton(
                onClick = { controller.navigate(Screen.Login.route) },
                modifier = Modifier.padding()
            ) {
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
        Text(
            "Home",
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun CenterRow(
    arrangement: Arrangement.Horizontal = Arrangement.Center,
    function: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = arrangement
    ) {
        function()
    }
}

@Composable
fun PasswordField(state: TextFieldState) {
    var showPassword by remember { mutableStateOf(false) }
    BasicSecureTextField(
        state = state,
        textObfuscationMode = if (showPassword) {
            TextObfuscationMode.Visible
        } else {
            TextObfuscationMode.RevealLastTyped
        },
        modifier = Modifier
            .fillMaxWidth()
            .borderBottom(),
        decorator = { innerTextField ->
            Box(Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp, end = 8.dp)
                ) {
                    innerTextField()
                }
                Icon(
                    if (showPassword) {
                        painterResource(R.drawable.baseline_visibility_24)
                    } else {
                        painterResource(R.drawable.outline_visibility_off_24)
                    },
                    contentDescription = "Toggle Password Visibility",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .requiredSize(36.dp)
                        .padding(8.dp)
                        .clickable(enabled = true, onClick = { showPassword = !showPassword })
                )
            }
        }
    )
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

