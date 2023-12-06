package com.example.practicaproductosapi.api

import com.example.practicapersonasapi.models.BaseResponse
import com.example.practicapersonasapi.models.Productos
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductosAPI {
        @GET("productos")
        fun getProductosList(): Call<List<Productos>>

        @GET("productos/{id}")
        fun getProductos(@Path("id") id: Int): Call<Productos>

        @POST("productos")
        fun createProductos(@Body productos: Productos): Call<Productos>

        @PATCH("productos/{id}")
        fun updateProductos(@Path("id") id: Int, @Body product: Productos): Call<Productos>

        @DELETE("productos/{id}")
        fun deleteProductos(@Path("id") id: Int): Call<BaseResponse>
}