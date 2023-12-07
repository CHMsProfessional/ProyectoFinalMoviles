package com.example.practicapersonasapi.ui.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicapersonasapi.databinding.ActivityReservesUserBinding
import com.example.practicapersonasapi.repositories.ReservasRepository
import com.example.practicapersonasapi.ui.adapter.PeliculaEstrenoAdapter
import com.example.practicapersonasapi.ui.adapter.ReservasAdapter
import com.example.practicaproductosapi.repositories.PeliculasRepository

class ReservesUserActivity : AppCompatActivity(), ReservasAdapter.onReservasItemListener {

    private lateinit var binding: ActivityReservesUserBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var accessToken: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservesUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        accessToken = sharedPreferences.getString("accessToken", null)

        setupReciclerView()
    }

    private fun setupReciclerView() {
        val adapter = ReservasAdapter(ArrayList(), this)
        binding.rvReservas.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(this@ReservesUserActivity,
                LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchReservas()
        accessToken = sharedPreferences.getString("accessToken", null)
    }

    private fun fetchReservas() {
        ReservasRepository.getReservasUser(
            accessToken,
            success = { rReservaList ->
                Log.d("reservas", "Reservas: "+rReservaList.toString())

                val adapter = binding.rvReservas.adapter as ReservasAdapter
                adapter.updateData(rReservaList ?: ArrayList())
            },
            failure = { _ ->
                Toast.makeText(this, "Error al cargar datos", Toast.LENGTH_SHORT).show()
            }
        )
    }
}