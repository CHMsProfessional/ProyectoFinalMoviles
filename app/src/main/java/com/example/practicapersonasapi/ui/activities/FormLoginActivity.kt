package com.example.practicapersonasapi.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.practicapersonasapi.databinding.ActivityFormLoginBinding
import com.example.practicapersonasapi.models.User
import com.example.practicapersonasapi.repositories.UserRepository

class FormLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var accessToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        accessToken = sharedPreferences.getString("accessToken", null)

        setupEventListeners()
    }

    private fun setupEventListeners(){
        binding.btnLogin.setOnClickListener {
            doLogin()
        }
        binding.btnRegisterL.setOnClickListener {
            startActivity(Intent(this, FormRegisterActivity::class.java))
        }
    }

    private fun doLogin(){
        val user = User(
            name = "",
            email = binding.txtEmail.text.toString(),
            password = binding.txtPassword.text.toString()
        )

        UserRepository.loginUser(user, success = {
            Toast.makeText(this, "Login realizado con exito!", Toast.LENGTH_SHORT).show()

            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("isLoggedIn", true)
            saveToken(user.authToken)
            saveRole(user.roles)
            Log.d("roles", "Roles: "+user.roles.toString() )
            editor.apply()

            finish()

            //startActivity(Intent(this, MainMenuActivty::class.java))

        }, failure = {
            Toast.makeText(this, "Error al hacer login", Toast.LENGTH_SHORT).show()
            finish()
        })

    }

    private fun saveRole(roles: List<String>?) {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("roles", roles.toString())
        editor.apply()
    }

    private fun saveToken(token: String?){
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("accessToken", token)
        editor.apply()
    }
}