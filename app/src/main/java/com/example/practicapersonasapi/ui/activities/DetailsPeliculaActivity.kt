package com.example.practicapersonasapi.ui.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.practicapersonasapi.databinding.ActivityDetailsPeliculaBinding
import com.example.practicapersonasapi.models.Horarios
import com.example.practicapersonasapi.ui.adapter.HorariosPeliculaAdapter
import com.example.practicaproductosapi.repositories.HorariosRepository

class DetailsPeliculaActivity : AppCompatActivity(),
    HorariosPeliculaAdapter.OnHorariosPeliculasItemListener
{
    private lateinit var binding: ActivityDetailsPeliculaBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var pelicula_id: Int = 0
    private var pelicula_sinopsis: String = ""
    private var pelicula_titulo: String = ""
    private var pelicula_imagen: String = ""
    private var pelicula_duracion: Int = 0
    private var accessToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsPeliculaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        accessToken = sharedPreferences.getString("accessToken", null)

        pelicula_id = intent.getIntExtra("id", 0)
        pelicula_sinopsis = intent.getStringExtra("sinopsis").toString()
        pelicula_titulo = intent.getStringExtra("titulo").toString()
        pelicula_imagen = intent.getStringExtra("imagen").toString()
        pelicula_duracion = intent.getIntExtra("duracion", 0)

        setupPoster()
        setupRecyclerViews()
    }

    private fun setupPoster() {
        binding.txtTitlePelicula.text = pelicula_titulo
        binding.txtSinopsisPelicula.text = pelicula_sinopsis
        binding.txtDuration.text = "Duracion: $pelicula_duracion minutos"
        Glide.with(this)
            .load("http://cineapi.jmacboy.com"+pelicula_imagen)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .fitCenter()
            .into(binding.imgPeliculaDetail)

    }

    private fun setupRecyclerViews() {
        val adapterHorariosSubtitulados = HorariosPeliculaAdapter(ArrayList(), this)
        binding.rvSubtitulada.apply {
            this.adapter = adapterHorariosSubtitulados
            this.layoutManager = LinearLayoutManager(this@DetailsPeliculaActivity,LinearLayoutManager.HORIZONTAL, false)
        }

        val adapterHorariosDoblados = HorariosPeliculaAdapter(ArrayList(), this)
        binding.rvDoblada.apply {
            this.adapter = adapterHorariosDoblados
            this.layoutManager = LinearLayoutManager(this@DetailsPeliculaActivity,LinearLayoutManager.HORIZONTAL, false)
        }

    }

    override fun onResume() {
        super.onResume()
        accessToken = sharedPreferences.getString("accessToken", null)
        fetchHorariosList(pelicula_id)
    }

    private fun fetchHorariosList(id: Int) {
        HorariosRepository.GetHorariosPelicula(
            accessToken,
            1,
            success = { horariosList ->
                Log.d("Horarios", horariosList.toString())
                val horariosFiltradosSubtitulados = horariosList?.filter { it.movie!!.language == 1 }
                val horariosFiltradosDoblados = horariosList?.filter { it.movie!!.language == 2 }

                Log.d("Horarios Subtitulados", horariosFiltradosSubtitulados.toString())
                Log.d("Horarios Doblados", horariosFiltradosDoblados.toString())

                // Actualizar el adaptador con la lista filtrada
                val adapterSubtitulado = binding.rvSubtitulada.adapter as HorariosPeliculaAdapter
                val adapterDoblado = binding.rvDoblada.adapter as HorariosPeliculaAdapter

                adapterSubtitulado.updateData(horariosFiltradosSubtitulados ?: ArrayList())
                adapterDoblado.updateData(horariosFiltradosDoblados ?: ArrayList())
            },
            failure = { _ ->
                Toast.makeText(this, "Error al cargar datos", Toast.LENGTH_SHORT).show()
            }
        )
    }
    override fun onHorariosPeliculasItemClick(horarios: Horarios) {
        TODO("Not yet implemented")
    }
}