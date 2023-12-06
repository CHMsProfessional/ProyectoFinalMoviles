package com.example.practicapersonasapi.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.practicapersonasapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var accessToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        // Obtiene el valor de accessToken después de inicializar sharedPreferences
        accessToken = sharedPreferences.getString("accessToken", null)

        setupButtons()
    }

    private fun setupButtons() {
        if (accessToken == null) {
            binding.btnIniciarSesion.text = "Iniciar Sesión"
            binding.btnIniciarSesion.setOnClickListener {
                val intent = Intent(this, FormLoginActivity::class.java)
                startActivity(intent)
            }
        } else {
            binding.btnIniciarSesion.text = "Logout"
            binding.btnIniciarSesion.setOnClickListener {
                // Realiza las acciones de cierre de sesión
                editor.clear()
                editor.apply()
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        binding.btnCartelera.setOnClickListener {
            val intent = Intent(this, CatalogoActivity::class.java)
            startActivity(intent)
        }

        if (accessToken == null) {
            binding.btnCartelera.visibility = View.GONE
        } else {
            binding.btnCartelera.visibility = View.VISIBLE
        }
    }
    override fun onResume() {
        super.onResume()
        accessToken = sharedPreferences.getString("accessToken", null)
        setupButtons()
        Log.d("MainActivity", "onResume: $accessToken")
    }


    }




