package com.example.practicapersonasapi.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.practicapersonasapi.R
import com.example.practicapersonasapi.databinding.ActivityReservePeliculaBinding
import com.example.practicapersonasapi.databinding.ActivitySeatsPeliculaBinding
import com.example.practicapersonasapi.models.CReserva
import com.example.practicapersonasapi.repositories.ReservasRepository

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
            asientosSeleccionados = it.getIntegerArrayList("asientos_seleccionados") as ArrayList<Int>
        }

        Log.d("cantidad_boletos", "Cantidad boletos activity Reserve: $cantidad_boletos")
        Log.d("horario_id", "Horario ID activity Reserve: $horario_id")
        Log.d("precio_boleto", "Precio boleto activity Reserve: $precio_boleto")
        Log.d("asientosSeleccionados", "Asientos seleccionados activity Reserve: $asientosSeleccionados")

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        accessToken = sharedPreferences.getString("accessToken", null)

        binding.labelPrecioFinal.text = "Precio final: $${cantidad_boletos * precio_boleto}"

        setupEventListeners()
    }

    private fun setupEventListeners() {
        binding.btnReservaFinal.setOnClickListener {
            reservar()
        }
    }

    private fun reservar() {

        var card_number = binding.txtNumeroDeTarjeta.text.toString()
        if (card_number.length != 16) {
            Toast.makeText(this, "Escriba un NÚMERO DE TARJETA VÁLIDO", Toast.LENGTH_SHORT).show()
            return
        }
        var card_name = binding.txtNombreFinal.text.toString()
        val anoFinal = binding.txtAnoFinal.text.toString()
        val mesFinal = binding.txtMesFinal.text.toString()

        if (mesFinal.length == 1) {
            binding.txtMesFinal.setText("0" + binding.txtMesFinal.text.toString())
        }
        if (anoFinal.length == 1) {
            binding.txtAnoFinal.setText("0" + binding.txtAnoFinal.text.toString())
        } else if (anoFinal.length != 2) {
            Toast.makeText(this, "Escriba un AÑO VÁLIDO", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.txtCVV.text.toString().length != 3) {
            Toast.makeText(this, "Escriva un CVV VALIDO", Toast.LENGTH_SHORT).show()
            return
        }

        var card_date = binding.txtMesFinal.text.toString() + "/" + binding.txtAnoFinal.text.toString()

        var reserva = CReserva(
            horario_id,
            card_number,
            card_name,
            card_date,
            binding.txtCVV.text.toString(),
            asientosSeleccionados
        )

        Log.d("Reserva", "Reserva: $reserva")

        ReservasRepository.crearReserva(
            accessToken,
            reserva,
            {
                Log.d("ReservaExitosa", "Reserva exitosa")
                reiniciarApp()
            },
            {
                Log.d("ReservaError", "Error en la reserva")
            }
        )
    }
    private fun reiniciarApp() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }
}