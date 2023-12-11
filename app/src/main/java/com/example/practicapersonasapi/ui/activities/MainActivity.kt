package com.example.practicapersonasapi.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practicapersonasapi.databinding.ActivityMainBinding
import com.example.practicapersonasapi.models.Horario
import com.example.practicapersonasapi.models.User
import com.example.practicapersonasapi.repositories.UserRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var accessToken: String? = null
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

    }

    private fun setupButtons() {
        if (accessToken == null) {
            binding.btnIniciarSesion.text = "Iniciar Sesión"
            binding.btnIniciarSesion.setOnClickListener {
                val intent = Intent(this, FormLoginActivity::class.java)
                startActivity(intent)
            }
            binding.btnCartelera.visibility = View.GONE
            binding.btnReservas.visibility = View.GONE
        } else {
            binding.btnIniciarSesion.text = "Logout"
            binding.btnIniciarSesion.setOnClickListener {
                editor.clear()
                editor.apply()
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }


            user?.let { currentUser ->
                if (currentUser.roles!!.isNotEmpty()) {
                    if (currentUser.roles!![0] == "Admin") {
                        binding.btnCartelera.text = "Administrar Reservas"
                        binding.btnCartelera.setOnClickListener {
                            val intent = Intent(this, ReservasAdminActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        binding.btnCartelera.text = "Cartelera"
                        binding.btnCartelera.setOnClickListener {
                            val intent = Intent(this, CatalogoActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }

            binding.btnReservas.setOnClickListener {
                val intent = Intent(this, ReservesUserActivity::class.java)
                startActivity(intent)
            }

            if (user?.roles?.getOrNull(0) == "Admin") {
                binding.btnCartelera.visibility = View.VISIBLE
                binding.btnReservas.visibility = View.GONE
            } else {
                binding.btnCartelera.visibility = View.VISIBLE
                binding.btnReservas.visibility = View.VISIBLE
            }
        }
    }
    private fun getUserMe() {
        accessToken?.let {
            UserRepository.getUser(
                it,
                success = { rUser ->
                    user = rUser
                    Log.d("MainActivity", "User: $user")
                    setupButtons()
                },
                failure = { _ ->
                    Toast.makeText(this, "Error al cargar datos", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        accessToken = sharedPreferences.getString("accessToken", null)
        if (accessToken != null) {
            getUserMe()
        } else {
            // Llamar a setupButtons directamente si no hay accessToken
            setupButtons()
        }
        Log.d("MainActivity", "onResume: $accessToken")
    }


}




