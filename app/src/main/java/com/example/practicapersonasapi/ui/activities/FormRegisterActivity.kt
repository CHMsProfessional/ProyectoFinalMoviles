package com.example.practicapersonasapi.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.practicapersonasapi.databinding.ActivityFormRegisterBinding
import com.example.practicapersonasapi.models.User
import com.example.practicapersonasapi.repositories.UserRepository

class FormRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEventListeners()
    }

    private fun setupEventListeners(){
        binding.btnRegister.setOnClickListener {
            doRegister()
        }
    }

    private fun doRegister() {
        val user = User(
            name = binding.txtNombreR.text.toString(),
            email = binding.txtEmailR.text.toString(),
            password = binding.txtContraR.text.toString()
        )

        UserRepository.registerUser(user, success = {
            Toast.makeText(this, "Registro realizado con exito!", Toast.LENGTH_SHORT).show()

            finish()

        }, failure = {
            Toast.makeText(this, "Error al hacer registro", Toast.LENGTH_SHORT).show()
            finish()
        })
    }

}