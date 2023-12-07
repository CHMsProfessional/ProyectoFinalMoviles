package com.example.practicapersonasapi.ui.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.practicapersonasapi.R
import com.example.practicapersonasapi.databinding.ActivityReservePeliculaBinding
import com.example.practicapersonasapi.databinding.ActivitySeatsPeliculaBinding

class ReservePeliculaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservePeliculaBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var accessToken: String? = null
    private var horario_id: Int = 0
    private var cantidad_boletos: Int = 0
    private var precio_boleto: Int = 0
    private var asientosSeleccionados = arrayListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservePeliculaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            cantidad_boletos = it.getInt("cantidad_boletos")
            horario_id = it.getInt("horario_id")
            precio_boleto = it.getInt("precio_boleto")
            asientosSeleccionados = it.getIntegerArrayList("asientosSeleccionados") as ArrayList<Int>
        }

        Log.d("cantidad_boletos", "Cantidad boletos activity Reserve: $cantidad_boletos")
        Log.d("horario_id", "Horario ID activity Reserve: $horario_id")
        Log.d("precio_boleto", "Precio boleto activity Reserve: $precio_boleto")
        Log.d("asientosSeleccionados", "Asientos seleccionados activity Reserve: $asientosSeleccionados")

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        accessToken = sharedPreferences.getString("accessToken", null)
    }
}