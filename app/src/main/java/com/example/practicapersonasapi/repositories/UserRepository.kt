package com.example.practicapersonasapi.repositories

import android.util.Log
import com.example.practicapersonasapi.api.AutenticacionAPI
import com.example.practicapersonasapi.models.AuthUser
import com.example.practicapersonasapi.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {

    fun registerUser(user: User, success: (User?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(AutenticacionAPI::class.java)
        api.registerUser(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    success(user)
                    Log.d("RegistroExitoso", "Registro exitoso")
                } else {
                    val errorMessage = "Error en el registro: ${response.code()}"
                    Log.e("RegistroError", errorMessage)
                    failure(Throwable(errorMessage))
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
                Log.e("SolicitudError", "Error en la solicitud: ${t.message}")
                failure(t)
            }
        })
    }

    fun loginUser(user: User, success: (User?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(AutenticacionAPI::class.java)
        api.loginUser(user).enqueue(object : Callback<AuthUser> {
            override fun onResponse(call: Call<AuthUser>, response: Response<AuthUser>) {
                if (response.isSuccessful) {
                    val authUser = response.body()
                    authUser?.let {
                        //almacenar el token en User
                        user.authToken = it.access_token
                        success(user)

                        Log.d("LoginExitoso", "Token recibido: ${it.access_token}")
                    }
                    Log.d("LoginExitoso", "Login exitoso")
                } else {
                    val errorMessage = "Error al hacer login: ${response.code()}"
                    Log.e("LoginError", errorMessage)
                    failure(Throwable(errorMessage))
                }
            }

            override fun onFailure(call: Call<AuthUser>, t: Throwable) {
                t.printStackTrace()
                Log.e("SolicitudError (Login)", "Error en la solicitud: ${t.message}")
                failure(t)
            }
        })
    }

    fun getUser(token: String, success: (User?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(AutenticacionAPI::class.java)
        val authorizationHeader = "Bearer $token"

        api.getUser(authorizationHeader).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    success(user)
                } else {
                    Log.e("GetUserError", "Error al obtener usuario: ${response.code()}")
                    failure(Throwable("Error al obtener usuario: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
                Log.e("SolicitudError (GetUser)", "Error en la solicitud: ${t.message}")
                failure(t)
            }

        })
    }
}