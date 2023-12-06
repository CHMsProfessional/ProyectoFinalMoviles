package com.example.practicaproductosapi.repositories

import com.example.practicapersonasapi.models.BaseResponse
import com.example.practicapersonasapi.models.Productos
import com.example.practicapersonasapi.repositories.RetrofitRepository
import com.example.practicaproductosapi.api.ProductosAPI
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ProductosRepository {
    fun getAll(success: (List<Productos>?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(ProductosAPI::class.java)
        api.getProductosList().enqueue(object : Callback<List<Productos>> {

            override fun onResponse(call: Call<List<Productos>>, response: Response<List<Productos>>) {
                val productos = response.body()
                success(productos)
            }

            override fun onFailure(call: Call<List<Productos>>, t: Throwable) {
                t.printStackTrace()
                failure(t)
            }
        })
    }

    fun insertProductos(productos: Productos, success: (Productos?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(ProductosAPI::class.java)



        api.createProductos(productos).enqueue(object : Callback<Productos> {
            override fun onResponse(call: Call<Productos>, response: Response<Productos>) {
                val productos = response.body()
                success(productos)
            }

            override fun onFailure(call: Call<Productos>, t: Throwable) {
                t.printStackTrace()
                failure(t)
            }
        })
    }

    fun getProductosById(id: Int, success: (Productos?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(ProductosAPI::class.java)
        api.getProductos(id).enqueue(object : Callback<Productos> {
            override fun onResponse(call: Call<Productos>, response: Response<Productos>) {
                val persona = response.body()
                success(persona)
            }

            override fun onFailure(call: Call<Productos>, t: Throwable) {
                t.printStackTrace()
                failure(t)
            }
        })
    }

    fun updateProductos(productos: Productos, success: (Productos?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(ProductosAPI::class.java)

        api.updateProductos(productos.id, productos).enqueue(object : Callback<Productos> {
            override fun onResponse(call: Call<Productos>, response: Response<Productos>) {
                val persona = response.body()
                success(persona)
            }

            override fun onFailure(call: Call<Productos>, t: Throwable) {
                t.printStackTrace()
                failure(t)
            }
        })
    }

    fun deleteProductos(id: Int, success: (BaseResponse?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofit()

        val api = retrofit.create(ProductosAPI::class.java)
        api.deleteProductos(id).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                val res = response.body()
                success(res)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                t.printStackTrace()
                failure(t)
            }
        })

    }
}