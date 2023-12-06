package com.example.practicapersonasapi.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicapersonasapi.databinding.ActivityCatalogoBinding
import com.example.practicapersonasapi.models.Pelicula
import com.example.practicapersonasapi.ui.adapter.PeliculaCarteleraAdapter
import com.example.practicapersonasapi.ui.adapter.PeliculaEstrenoAdapter
import com.example.practicapersonasapi.ui.adapter.PeliculaPreventaAdapter
import com.example.practicapersonasapi.ui.adapter.PeliculaProximamenteAdapter
import com.example.practicaproductosapi.repositories.PeliculasRepository

class CatalogoActivity : AppCompatActivity(),
    PeliculaEstrenoAdapter.OnPeliculasEstrenoItemListener,
    PeliculaCarteleraAdapter.OnPeliculasCarteleraItemListener,
    PeliculaPreventaAdapter.OnPeliculasPreventaItemListener,
    PeliculaProximamenteAdapter.OnPeliculasProximamenteItemListener
{
    private lateinit var binding: ActivityCatalogoBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var accessToken: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        // Obtiene el valor de accessToken después de inicializar sharedPreferences
        accessToken = sharedPreferences.getString("accessToken", null)

        setupRecyclerViews()
    }

    private fun setupRecyclerViews() {
        val adapterEstrenos = PeliculaEstrenoAdapter(ArrayList(), this)
        binding.rvEstrenos.apply {
            this.adapter = adapterEstrenos
            this.layoutManager = LinearLayoutManager(this@CatalogoActivity,LinearLayoutManager.HORIZONTAL, false)
        }

        val adapterCartelera = PeliculaCarteleraAdapter(ArrayList(), this)
        binding.rvCartelera.apply {
            this.adapter = adapterCartelera
            this.layoutManager = LinearLayoutManager(this@CatalogoActivity,LinearLayoutManager.HORIZONTAL, false)
        }

        val adapterPreventa = PeliculaPreventaAdapter(ArrayList(), this)
        binding.rvPreventa.apply {
            this.adapter = adapterPreventa
            this.layoutManager = LinearLayoutManager(this@CatalogoActivity,LinearLayoutManager.HORIZONTAL, false)
        }

        val adapterProximamente = PeliculaProximamenteAdapter(ArrayList(), this)
        binding.rvProximamente.apply {
            this.adapter = adapterProximamente
            this.layoutManager = LinearLayoutManager(this@CatalogoActivity,LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchPeliculasList()
        accessToken = sharedPreferences.getString("accessToken", null)
    }

    private fun fetchPeliculasList() {
        PeliculasRepository.getPeliculasEstrenoList(
            accessToken,
            success = { peliculaEstrenoList ->
                val adapter = binding.rvEstrenos.adapter as PeliculaEstrenoAdapter
                adapter.updateData(peliculaEstrenoList ?: ArrayList())
            },
            failure = { _ ->
                Toast.makeText(this, "Error al cargar datos", Toast.LENGTH_SHORT).show()
            }
        )

        PeliculasRepository.getPeliculasCarteleraList(
            accessToken,
            success = { peliculaCarteleraList ->
                val adapter = binding.rvCartelera.adapter as PeliculaCarteleraAdapter
                adapter.updateData(peliculaCarteleraList ?: ArrayList())
            },
            failure = { _ ->
                Toast.makeText(this, "Error al cargar datos", Toast.LENGTH_SHORT).show()
            }
        )

        PeliculasRepository.getPeliculasPreventaList(
            accessToken,
            success = { peliculaPreventaList ->
                val adapter = binding.rvPreventa.adapter as PeliculaPreventaAdapter
                adapter.updateData(peliculaPreventaList ?: ArrayList())
            },
            failure = { _ ->
                Toast.makeText(this, "Error al cargar datos", Toast.LENGTH_SHORT).show()
            }
        )

        PeliculasRepository.getPeliculasProximamenteList(
            accessToken,
            success = { peliculaProximamenteList ->
                val adapter = binding.rvProximamente.adapter as PeliculaProximamenteAdapter
                adapter.updateData(peliculaProximamenteList ?: ArrayList())
            },
            failure = { _ ->
                Toast.makeText(this, "Error al cargar datos", Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun onPeliculasEstrenoItemClick(peliculas: Pelicula) {
        abrirDetallesPelicula(peliculas)
    }

    override fun onPeliculasCarteleraItemClick(peliculas: Pelicula) {
        abrirDetallesPelicula(peliculas)
    }

    override fun onPeliculasPreventaItemClick(peliculas: Pelicula) {
        abrirDetallesPelicula(peliculas)
    }

    override fun onPeliculasProximamenteItemClick(peliculas: Pelicula) {
        abrirDetallesPelicula(peliculas)
    }

    private fun abrirDetallesPelicula(pelicula: Pelicula) {
        val intent = Intent(this, DetailsPeliculaActivity::class.java)
        intent.putExtra("id", pelicula.id)
        intent.putExtra("sinopsis", pelicula.synopsis)
        intent.putExtra("titulo", pelicula.title)
        intent.putExtra("imagen", pelicula.posterUrl)
        intent.putExtra("duracion", pelicula.durationMin)

        // Inicia la actividad
        startActivity(intent)
    }
}