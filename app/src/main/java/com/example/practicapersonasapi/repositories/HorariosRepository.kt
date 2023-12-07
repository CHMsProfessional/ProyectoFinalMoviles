package com.example.practicaproductosapi.repositories


import com.example.practicapersonasapi.models.GrupoHorarios
import com.example.practicapersonasapi.models.Horario
import com.example.practicapersonasapi.repositories.RetrofitRepository
import com.example.practicaproductosapi.api.HorariosAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object HorariosRepository {

    private fun getAuthorizationHeader(accessToken: String?): String {
        return "Bearer $accessToken"
    }

    fun GetHorariosPelicula(
        accessToken: String?,
        idPelicula: Int,
        success: (List<Horario>?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(HorariosAPI::class.java)
        api.getHorariosPelicula(getAuthorizationHeader(accessToken),idPelicula)
            .enqueue(object : Callback<List<Horario>> {

                override fun onResponse(call: Call<List<Horario>>, response: Response<List<Horario>>) {
                    val horarios = response.body()
                    success(horarios)
                }

                override fun onFailure(call: Call<List<Horario>>, t: Throwable) {
                    t.printStackTrace()
                    failure(t)
                }
            })
    }
}