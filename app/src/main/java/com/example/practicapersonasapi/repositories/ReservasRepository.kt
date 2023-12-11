package com.example.practicapersonasapi.repositories

import android.util.Log
import com.example.practicapersonasapi.api.ReservasAPI
import com.example.practicapersonasapi.models.CReserva
import com.example.practicapersonasapi.models.QRCodeReserva
import com.example.practicapersonasapi.models.RReserva
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ReservasRepository {

    private fun getAuthorizationHeader(accessToken: String?): String {
        return "Bearer $accessToken"
    }
    fun crearReserva(
        accessToken: String?,
        reserva: CReserva,
        success: (RReserva?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(ReservasAPI::class.java)
        api.createReserva(getAuthorizationHeader(accessToken), reserva).enqueue(object : Callback<RReserva> {
            override fun onResponse(call: Call<RReserva>, response: Response<RReserva>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    success(user)
                    Log.d("RegistroExitoso", "Registro exitoso")
                } else {
                    val errorMessage = "Error en el registro: ${response.errorBody().toString()}"
                    Log.e("RegistroError", errorMessage)
                    failure(Throwable(errorMessage))
                }
            }

            override fun onFailure(call: Call<RReserva>, t: Throwable) {
                t.printStackTrace()
                Log.e("SolicitudError", "Error en la solicitud: ${t.message}")
                failure(t)
            }
        })
    }

    fun getReservasUser(
        accessToken: String?,
        success: (List<RReserva>?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(ReservasAPI::class.java)
        api.getReservasUsuario(getAuthorizationHeader(accessToken))
            .enqueue(object : Callback<List<RReserva>> {

                override fun onResponse(call: Call<List<RReserva>>, response: Response<List<RReserva>>) {
                    val horarios = response.body()
                    success(horarios)
                }

                override fun onFailure(call: Call<List<RReserva>>, t: Throwable) {
                    t.printStackTrace()
                    failure(t)
                }
            })
    }

    fun validarReserva(
        accessToken: String?,
        code: String?,
        success: (RReserva?) -> Unit,
        failure: (Throwable) -> Unit
    ) {

        val QRCodeReserva = QRCodeReserva(code)

        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(ReservasAPI::class.java)
        api.validateReserva(getAuthorizationHeader(accessToken), QRCodeReserva).enqueue(object : Callback<RReserva> {
            override fun onResponse(call: Call<RReserva>, response: Response<RReserva>) {
                if (response.isSuccessful) {
                    val reserva = response.body()
                    success(reserva)
                    Log.d("Validacion Exitosa", "Validacion exitosa")
                } else {
                    val errorMessage = "Error en el registro: ${response.errorBody().toString()}"
                    Log.e("Error EN LA VALIDACION", errorMessage)
                    failure(Throwable(errorMessage))
                }
            }

            override fun onFailure(call: Call<RReserva>, t: Throwable) {
                t.printStackTrace()
                Log.e("SolicitudError", "Error en la solicitud: ${t.message}")
                failure(t)
            }
        })
    }

    fun usarReserva(
        accessToken: String?,
        id: Int,
        code: String?,
        success: (RReserva?) -> Unit,
        failure: (Throwable) -> Unit
    ) {

        val QRCodeReserva = QRCodeReserva(code)

        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(ReservasAPI::class.java)
        api.useReserva(getAuthorizationHeader(accessToken), id,QRCodeReserva).enqueue(object : Callback<RReserva> {
            override fun onResponse(call: Call<RReserva>, response: Response<RReserva>) {
                if (response.isSuccessful) {
                    val reserva = response.body()
                    success(reserva)
                    Log.d("Validacion Exitosa", "Validacion exitosa")
                } else {
                    val errorMessage = "Error en el registro: ${response.errorBody().toString()}"
                    Log.e("Error EN LA VALIDACION", errorMessage)
                    failure(Throwable(errorMessage))
                }
            }

            override fun onFailure(call: Call<RReserva>, t: Throwable) {
                t.printStackTrace()
                Log.e("SolicitudError", "Error en la solicitud: ${t.message}")
                failure(t)
            }
        })
    }
}