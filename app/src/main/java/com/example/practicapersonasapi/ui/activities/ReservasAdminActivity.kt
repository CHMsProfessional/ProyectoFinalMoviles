package com.example.practicapersonasapi.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.practicapersonasapi.R
import com.example.practicapersonasapi.databinding.ActivityReservasAdminBinding
import com.example.practicapersonasapi.repositories.ReservasRepository
import com.google.zxing.integration.android.IntentIntegrator

class ReservasAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservasAdminBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var accessToken: String? = null
    private var currentAction: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservasAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        accessToken = sharedPreferences.getString("accessToken", null)

        setupEventListeners()
    }

    private fun setupEventListeners() {
        binding.btnValidar.setOnClickListener {
            currentAction = "Validar"
            initScanner()
        }
        binding.btnUsar.setOnClickListener() {
            currentAction = "Usar"
            initScanner()
        }
    }

    override fun onResume() {
        super.onResume()
        accessToken = sharedPreferences.getString("accessToken", null)
    }

    private fun initScanner() {
        IntentIntegrator(this).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show()
            } else {
                val scannedValue = result.contents
                when (currentAction) {
                    "Validar" -> {
                        fetchValidarData(scannedValue)
                    }
                    "Usar" -> {
                        fetchUsarData(scannedValue)
                    }
                    else -> {
                        Toast.makeText(this, "Acción no reconocida", Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun fetchValidarData(scannedValue: String) {
        Toast.makeText(this, "Validando el QR: $scannedValue", Toast.LENGTH_LONG).show()
        ReservasRepository.validarReserva(
            accessToken,
            scannedValue,
         { reserva ->
            reserva?.let {
                if (it.message != null) {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    return@validarReserva
                } else {
                    Toast.makeText(this, "Reserva validada", Toast.LENGTH_LONG).show()
                }
            }
        },
        {
            Toast.makeText(this, "Esta Reserva no Existe o Ya fue utilizada", Toast.LENGTH_LONG).show()
        })
    }

    private fun fetchUsarData(scannedValue: String) {

        Toast.makeText(this, "Usando la reserva con QR: $scannedValue", Toast.LENGTH_LONG).show()
        ReservasRepository.validarReserva(
            accessToken,
            scannedValue,
            { reserva ->
                reserva?.let { rReserva ->
                    if (rReserva.message != null) {
                        Toast.makeText(this, rReserva.message, Toast.LENGTH_LONG).show()
                        return@validarReserva
                    }else{
                        ReservasRepository.usarReserva(
                            accessToken,
                            rReserva.id,
                            scannedValue,
                            { reserva ->
                                reserva?.let {
                                    if (it.message != null) {
                                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                                        return@usarReserva
                                    } else {
                                        Toast.makeText(this, "Reserva UTILIZADA", Toast.LENGTH_LONG).show()
                                    }
                                }
                            },
                            {
                                Toast.makeText(this, "Error al usar la reserva", Toast.LENGTH_LONG).show()
                            })
                    }

                }
            },
            {
                Toast.makeText(this, "Esta Reserva no Existe o Ya fue utilizada", Toast.LENGTH_LONG).show()
            })
    }

}