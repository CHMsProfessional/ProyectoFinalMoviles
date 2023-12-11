package com.example.practicapersonasapi.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableRow
import android.widget.Toast
import com.example.practicapersonasapi.R
import com.example.practicapersonasapi.databinding.ActivitySeatsPeliculaBinding
import com.example.practicapersonasapi.models.Horario
import com.example.practicapersonasapi.models.Sala
import com.example.practicaproductosapi.repositories.HorariosRepository
import com.example.practicaproductosapi.repositories.SalasRepository

class SeatsPeliculaActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeatsPeliculaBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var accessToken: String? = null
    private var pelicula_id: Int = 0
    private var horario_id: Int = 0
    private var cantidad_boletos: Int = 0
    private var precio_boleto: Int = 0
    private var cantidad_boletos_seleccionados: Int = 0
    private var horario: Horario? = null
    private var sala: Sala? = null
    private var horarioFetched = false
    private var asientosSeleccionados = arrayListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatsPeliculaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            pelicula_id = it.getInt("pelicula_id")
            cantidad_boletos = it.getInt("cantidad_boletos")
            horario_id = it.getInt("horario_id")
            precio_boleto = it.getInt("precio_boleto")
        }

        Log.d("pelicula_id", "Pelicula ID activity Seats: "+pelicula_id.toString())
        Log.d("cantidad_boletos", "Cantidad boletos activity Seats: "+cantidad_boletos.toString())
        Log.d("horario_id", "Horario ID activity Seats: "+horario_id.toString())

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        accessToken = sharedPreferences.getString("accessToken", null)


        setupEventListeners()
    }

    override fun onResume() {
        super.onResume()
        accessToken = sharedPreferences.getString("accessToken", null)

        // Reiniciar la bandera
        horarioFetched = false

        // Iniciar la operación asíncrona
        fetchHorario()
    }

    private fun setupEventListeners() {
        binding.btnReservar.setOnClickListener {
            reservar()
        }
        binding.btnResetear.setOnClickListener {
            finish()
        }
    }

    private fun reservar() {
        if (asientosSeleccionados.isEmpty()) {
            Toast.makeText(this, "Selecciona al menos un asiento", Toast.LENGTH_SHORT).show()
            return
        }
        if (asientosSeleccionados.size != cantidad_boletos) {
            Toast.makeText(this, "Selecciona $cantidad_boletos asientos", Toast.LENGTH_SHORT).show()
            return
        }
        var Intent = Intent(this, ReservePeliculaActivity::class.java)
        Intent.putExtra("horario_id", horario_id)
        Intent.putExtra("asientos_seleccionados", asientosSeleccionados)
        Intent.putExtra("precio_boleto", precio_boleto)
        Log.d("horario_id", "Horario ID intent Seats: $horario_id")
        Log.d("precio_boleto", "Precio boleto intent Seats: $precio_boleto")
        Log.d("asientos_seleccionados", "Asientos seleccionados intent Seats: $asientosSeleccionados")

        startActivity(Intent)
    }

    private fun setupTable() {
        Log.d("SeatsPeliculaActivity", "setupTable: "+sala.toString())
        if (sala == null) {
            Toast.makeText(this, "Sala no encontrada", Toast.LENGTH_SHORT).show()
            return
        }

        sala?.let {
            val rows = it.rows
            val columns = it.columns

            val tableLayout = binding.tablaAsientos
            val asientos = it.seats

            for (row in 0 until rows) {
                val tableRow = TableRow(this)
                tableRow.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )

                for (column in 0 until columns) {
                    val imageView = ImageView(this)

                    // Cambios aquí para ajustar el tamaño de las imágenes
                    val imageSize = resources.getDimensionPixelSize(R.dimen.image_size) // Define la dimensión en dimens.xml
                    val params = TableRow.LayoutParams(
                        imageSize,
                        imageSize
                    )

                    imageView.layoutParams = params


                    imageView.id = asientos.find { it.row == row && it.column == column }?.id ?: -1 // Aquí obtienes el ID de los asientos


                    val asiento = asientos.find { it.row == row && it.column == column }
                    if (asiento == null || imageView.id == -1) {
                        imageView.setImageResource(R.drawable.nodisponible)
                        imageView.tag = "nodisponible"
                    }else if (!asiento.vacant) {
                        imageView.setImageResource(R.drawable.ocupado)
                        imageView.tag = "ocupado"
                    }else{
                        imageView.setImageResource(R.drawable.libre)
                        imageView.tag = "libre"
                    }


                    // Aquí estableces el OnClickListener para cambiar el estado al ser presionado
                    imageView.setOnClickListener { view ->
                        cambiarEstadoAsiento(view as ImageView)
                    }

                    tableRow.addView(imageView)
                }

                tableLayout.addView(tableRow)
            }
        }
    }

    private fun cambiarEstadoAsiento(imageView: ImageView) {
        val tag = imageView.tag as String
        if (cantidad_boletos_seleccionados == cantidad_boletos && tag == "libre") {
            Toast.makeText(this, "No puedes seleccionar más asientos", Toast.LENGTH_SHORT).show()
            return
        }
        if (tag == "libre") {
            imageView.setImageResource(R.drawable.seleccionado)
            imageView.tag = "seleccionado"
            asientosSeleccionados.add(imageView.id)
            cantidad_boletos_seleccionados++
        } else if (tag == "seleccionado") {
            imageView.setImageResource(R.drawable.libre)
            imageView.tag = "libre"
            asientosSeleccionados.remove(imageView.id)
            cantidad_boletos_seleccionados--
        } else if (tag == "ocupado") {
            Toast.makeText(this, "Este asiento no está disponible", Toast.LENGTH_SHORT).show()
        } else if (tag == "nodisponible") {
            Toast.makeText(this, "Este asiento no está disponible", Toast.LENGTH_SHORT).show()
        }

        Log.d("SeatsPeliculaActivity", "Asientos seleccionados: $asientosSeleccionados")
    }

    private fun fetchAsientos() {
        Log.d("SeatsPeliculaActivity", "fetchAsientos: " + horario.toString())
        if (horario == null) {
            Toast.makeText(this, "Horario no encontrado", Toast.LENGTH_SHORT).show()
            return
        }

        horario?.let { horario1 ->
            SalasRepository.getSalaWithVacant(
                accessToken,
                horario1.room_id,
                horario1.id,
                success = { sala ->
                    sala?.let {
                        this.sala = it
                    }
                    Log.d("SeatsPeliculaActivity", "fetchAsientos: " + sala.toString())
                    checkAndSetupTable()
                },
                failure = { _ ->
                    Toast.makeText(this, "Error al cargar datos", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun fetchHorario() {
        HorariosRepository.GetHorariosPelicula(
            accessToken,
            pelicula_id,
            success = { horarioList ->
                Log.d("Horarios", "Horarios activity Seats: " + horarioList.toString())
                horario = horarioList?.find { it.id == horario_id }
                Log.d("Horario", "Horario activity Seats: " + horario.toString())

                // Llamar a fetchAsientos dentro de fetchHorario
                fetchAsientos()

                // Establecer la bandera cuando la operación está completa
                horarioFetched = true

                // Llamar a setupTable solo cuando ambas operaciones estén completas
                checkAndSetupTable()
            },
            failure = { _ ->
                Toast.makeText(this, "Error al cargar datos", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun checkAndSetupTable() {
        // setupTable solo se llama cuando ambas operaciones están completas
        if (horarioFetched && sala != null) {
            setupTable()
        }
    }


}