package com.example.practicapersonasapi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicapersonasapi.R
import com.example.practicapersonasapi.models.Pelicula
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.practicapersonasapi.databinding.ReservaItemBinding
import com.example.practicapersonasapi.models.RReserva
import com.example.practicaproductosapi.repositories.PeliculasRepository
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class ReservasAdapter(private var reservasList: ArrayList<RReserva>, private val listener: onReservasItemListener,
                      private val accessToken: String?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val rowView = inflater.inflate(R.layout.reserva_item, parent, false)

        return ViewHolder(rowView, accessToken)
    }

    override fun getItemCount(): Int {
        return reservasList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pelicula = reservasList[position]
        val viewHolder = holder as ViewHolder
        viewHolder.bind(pelicula, listener)
    }

    fun updateData(reserva: List<RReserva>) {
        reservasList.clear()
        reservasList.addAll(reserva)
        notifyDataSetChanged()
    }

    class ViewHolder(
        itemView: View,
        private val accessToken: String?
    ) : RecyclerView.ViewHolder(itemView) {
        private val binding = ReservaItemBinding.bind(itemView)

        fun bind(reserva: RReserva, listener: onReservasItemListener) {
            val date = LocalDate.parse(reserva.schedule.date)
            val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd - EEEE")

            PeliculasRepository.getPelicula(
                accessToken,
                reserva.schedule.movie_id,
                success = { pelicula ->
                    Glide.with(itemView)
                        .load("http://cineapi.jmacboy.com"+ pelicula!!.posterUrl)
                        .placeholder(R.drawable.background)
                        .fitCenter()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.imgPeliculaR)
                },
                failure = { _ ->
                }
            )

            Glide.with(itemView)
                .load("http://cineapi.jmacboy.com"+reserva.qrUrl)
                .placeholder(R.drawable.background)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imgQR)


            binding.txtNombreQR.text = "A nombre de: "+reserva.user.name
            binding.txtFechaP.text = "Para el Dia:  "+date.format(dateFormat)
            binding.txtHoraP.text = "A la hora: "+reserva.schedule.time.dropLast(3)
            binding.txtPrecioR.text = "Precio total: "+reserva.totalPrice.toString()

            binding.txtAsientos.text = "Asientos: "+reserva.tickets.map { it.seat.id }.joinToString(", ")
        }

    }

    interface onReservasItemListener {
        //fun onPeliculasCarteleraItemClick(peliculas: Pelicula)
    }
}