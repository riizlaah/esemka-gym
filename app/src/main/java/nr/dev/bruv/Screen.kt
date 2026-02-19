package nr.dev.bruv

sealed class Screen(val route: String) {
    object Login: Screen("login")
    object SignUp: Screen("signup")
    object Home: Screen("home")
}