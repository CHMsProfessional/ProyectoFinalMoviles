package com.example.practicapersonasapi.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.practicapersonasapi.databinding.ActivityQuantityPeliculaBinding

class QuantityPeliculaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuantityPeliculaBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var accessToken: String? = null
    private var pelicula_id: Int = 0
    private var horario_id: Int = 0
    private var precio_boleto: Int = 0
    private var cantidad_boletos: Int = 0
    private var precio_final: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuantityPeliculaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            pelicula_id = it.getInt("pelicula_id")
            horario_id = it.getInt("horario_id")
            precio_boleto = it.getInt("precio_boleto")
        }
        Log.d("pelicula_id", "Pelicula ID activity Quantity: "+pelicula_id.toString())
        Log.d("horario_id", "Horario ID activity Quantity: "+horario_id.toString())
        Log.d("precio_boleto", "Precio boleto activity Quantity: "+precio_boleto.toString())

        binding.txtPrecioFinal.text = "$precio_boleto BS"

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        // Obtiene el valor de accessToken después de inicializar sharedPreferences
        accessToken = sharedPreferences.getString("accessToken", null)

        setupEventListeners()
    }

    private fun setupEventListeners() {
        binding.btnComprarTickets.setOnClickListener {
            intent = Intent(this, SeatsPeliculaActivity::class.java)
            intent.putExtra("pelicula_id", pelicula_id)
            intent.putExtra("horario_id", horario_id)
            intent.putExtra("precio_boleto", precio_boleto)
            intent.putExtra("cantidad_boletos", cantidad_boletos)
            Log.d("pelicula_id", "Pelicula ID intent Quantity: $pelicula_id")
            Log.d("cantidad_boletos", "Cantidad boletos intent Quantity: $cantidad_boletos")
            Log.d("horario_id", "Horario ID intent Quantity: $horario_id")
            Log.d("precio_boleto", "Precio boleto intent Quantity: $precio_boleto")

            startActivity(intent)
        }
        binding.btnAumentar.setOnClickListener {
            cantidad_boletos++
            precio_final = precio_boleto * cantidad_boletos
            binding.txtPrecioFinal.text = "$precio_final BS"
            binding.txtCantidad.text = cantidad_boletos.toString()
        }
        binding.btnDisminuir.setOnClickListener {
            if (cantidad_boletos > 1) {
                cantidad_boletos--
                precio_final = precio_boleto * cantidad_boletos
                binding.txtPrecioFinal.text = "$precio_final BS"
                binding.txtCantidad.text = cantidad_boletos.toString()
            }
        }
    }
}