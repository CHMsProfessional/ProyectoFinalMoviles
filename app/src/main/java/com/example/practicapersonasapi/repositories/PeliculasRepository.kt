package com.example.practicaproductosapi.repositories


import com.example.practicapersonasapi.api.PeliculasAPI
import com.example.practicapersonasapi.models.Pelicula
import com.example.practicapersonasapi.repositories.RetrofitRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PeliculasRepository {

    private fun getAuthorizationHeader(accessToken: String?): String {
        return "Bearer $accessToken"
    }

    fun getPeliculasEstrenoList(
        accessToken: String?,
        success: (List<Pelicula>?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(PeliculasAPI::class.java)
        api.getPeliculasEstrenosList(getAuthorizationHeader(accessToken))
            .enqueue(object : Callback<List<Pelicula>> {

                override fun onResponse(call: Call<List<Pelicula>>, response: Response<List<Pelicula>>) {
                    val peliculas = response.body()
                    success(peliculas)
                }

                override fun onFailure(call: Call<List<Pelicula>>, t: Throwable) {
                    t.printStackTrace()
                    failure(t)
                }
            })
    }

    fun getPeliculasPreventaList(
        accessToken: String?,
        success: (List<Pelicula>?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(PeliculasAPI::class.java)
        api.getPeliculasPreventaList(getAuthorizationHeader(accessToken)).enqueue(object : Callback<List<Pelicula>> {

            override fun onResponse(call: Call<List<Pelicula>>, response: Response<List<Pelicula>>) {
                val peliculas = response.body()
                success(peliculas)
            }

            override fun onFailure(call: Call<List<Pelicula>>, t: Throwable) {
                t.printStackTrace()
                failure(t)
            }
        })
    }

    fun getPeliculasCarteleraList(
        accessToken: String?,
        success: (List<Pelicula>?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(PeliculasAPI::class.java)
        api.getPeliculasCarteleraList(getAuthorizationHeader(accessToken)).enqueue(object : Callback<List<Pelicula>> {

            override fun onResponse(call: Call<List<Pelicula>>, response: Response<List<Pelicula>>) {
                val peliculas = response.body()
                success(peliculas)
            }

            override fun onFailure(call: Call<List<Pelicula>>, t: Throwable) {
                t.printStackTrace()
                failure(t)
            }
        })
    }

    fun getPeliculasProximamenteList(
        accessToken: String?,
        success: (List<Pelicula>?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(PeliculasAPI::class.java)
        api.getPeliculasProximamenteList(getAuthorizationHeader(accessToken)).enqueue(object : Callback<List<Pelicula>> {

            override fun onResponse(call: Call<List<Pelicula>>, response: Response<List<Pelicula>>) {
                val peliculas = response.body()
                success(peliculas)
            }

            override fun onFailure(call: Call<List<Pelicula>>, t: Throwable) {
                t.printStackTrace()
                failure(t)
            }
        })
    }

    fun getPelicula(
        accessToken: String?,
        id: Int,
        success: (Pelicula?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(PeliculasAPI::class.java)
        api.getPelicula(getAuthorizationHeader(accessToken), id).enqueue(object : Callback<Pelicula> {

            override fun onResponse(call: Call<Pelicula>, response: Response<Pelicula>) {
                val pelicula = response.body()
                success(pelicula)
            }

            override fun onFailure(call: Call<Pelicula>, t: Throwable) {
                t.printStackTrace()
                failure(t)
            }
        })
    }
}