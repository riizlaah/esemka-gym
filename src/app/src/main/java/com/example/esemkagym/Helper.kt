package com.example.esemkagym

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

data class HttpReq(
    val url: String,
    val method: String = "GET",
    val body: String = "",
    val headers: Map<String, String> = emptyMap(),
    val timeout: Int = 10000
)

data class HttpRes(
    val code: Int,
    val body: String? = null,
    val error: String? = null
)

object HttpClient {
    val addr = "http://10.0.2.2:8081/api"
    var token = ""
    lateinit var prefs: SharedPreferences

    fun loadToken() {
        token = prefs.getString("token", "") ?: ""
    }

    fun saveToken() {
        prefs.edit(true) {
            putString("token", token)
        }
    }

    fun send(req: HttpReq): HttpRes {
        val conn = URL(req.url).openConnection() as HttpURLConnection
        return try {
            conn.run {
                requestMethod = req.method
                readTimeout = req.timeout
                connectTimeout = req.timeout
                req.headers.forEach { k, v -> setRequestProperty(k, v) }
                if (req.body.isNotBlank() && req.method in listOf("POST", "PUT", "PATCH")) {
                    getOutputStream().buffered().use { it.write(req.body.toByteArray()) }
                }

                connect()
                val code = responseCode
                val body = if (code in 200..299) {
                    getInputStream().bufferedReader().use { it.readText() }
                } else {
                    errorStream?.bufferedReader()?.use { it.readText() }
                }
                HttpRes(code, body)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            HttpRes(-1, error = e.message ?: "Network error")
        } finally {
            conn.disconnect()
        }
    }

    suspend fun jsonReq(
        route: String,
        method: String = "GET",
        body: String = "",
        errMsg: String = "Error",
        onSuccess: JSONObject.() -> Unit = {}
    ): String {
        val headers = if (token.isEmpty()) mapOf("content-type" to "application/json") else mapOf(
            "content-type" to "application/json",
            "authorization" to "Bearer $token"
        )
        val res = withContext(Dispatchers.IO) {
            send(HttpReq(addr + route, method, body, headers))
        }
        if (res.body.isNullOrBlank()) {
            Log.d("JSONReqErr", res.toString())
            return "$errMsg;${res.code}"
        }
        return try {
            val json = JSONObject(res.body)
            if (res.code in 200..299) {
                json.run(onSuccess)
                "ok"
            } else {
                json.optString("message", errMsg)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            errMsg
        }
    }

    suspend fun login(email: String, password: String): String {
        return jsonReq(
            "login", "POST", """{
  "email": "$email",
  "password": "$password"
}"""
        ) {
            token = getString("token")
            saveToken()
        }
    }


    suspend fun signup(email: String, gender: String, name: String, password: String): String {
        return jsonReq(
            "signup", "POST", """{
  "email": "$email",
  "gender": "$gender",
  "name": "$name",
  "password": "$password"
}"""
        ) {}
    }

    suspend fun checkAuthStatus(): String {

        val isMember = Regex("(;4\\d{2})$").matches(jsonReq("member"))
        val isAdmin = Regex("(;4\\d{2})$").matches(jsonReq("attendance/checkout"))
        if(!isAdmin && !isMember) return ""
        return if(isAdmin) "admin" else "member"
    }

}