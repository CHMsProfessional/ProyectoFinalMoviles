package com.example.practicaproductosapi.repositories


import com.example.practicapersonasapi.models.Sala
import com.example.practicapersonasapi.models.ScheduleIDRequest
import com.example.practicapersonasapi.repositories.RetrofitRepository
import com.example.practicaproductosapi.api.SalasAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SalasRepository {

    private fun getAuthorizationHeader(accessToken: String?): String {
        return "Bearer $accessToken"
    }

    fun getSala(
        accessToken: String?,
        idSala: Int,
        success: (Sala?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(SalasAPI::class.java)
        api.getSala(getAuthorizationHeader(accessToken), idSala)
            .enqueue(object : Callback<Sala> {

                override fun onResponse(call: Call<Sala>, response: Response<Sala>) {
                    val sala = response.body()
                    success(sala)
                }

                override fun onFailure(call: Call<Sala>, t: Throwable) {
                    t.printStackTrace()
                    failure(t)
                }
            })
    }

    fun getSalaWithVacant(
        accessToken: String?,
        idSala: Int,
        scheduleId: Int,
        success: (Sala?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(SalasAPI::class.java)

        val request = ScheduleIDRequest(scheduleId)

        api.getSalaWithVacant(getAuthorizationHeader(accessToken), idSala, request)
            .enqueue(object : Callback<Sala> {

                override fun onResponse(call: Call<Sala>, response: Response<Sala>) {
                    val sala = response.body()
                    success(sala)
                }

                override fun onFailure(call: Call<Sala>, t: Throwable) {
                    t.printStackTrace()
                    failure(t)
                }
            })
    }
}