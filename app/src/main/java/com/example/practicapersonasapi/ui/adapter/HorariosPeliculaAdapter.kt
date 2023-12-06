package com.example.practicapersonasapi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicapersonasapi.R
import com.example.practicapersonasapi.databinding.HorariosItemBinding
import com.example.practicapersonasapi.models.Horarios
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class HorariosPeliculaAdapter(private var horarioPeliculaList: ArrayList<Horarios>, private val listener: OnHorariosPeliculasItemListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val rowView = inflater.inflate(R.layout.horarios_item, parent, false)

        return ViewHolder(rowView)
    }

    override fun getItemCount(): Int {
        return horarioPeliculaList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val horarios = horarioPeliculaList[position]
        val viewHolder = holder as ViewHolder
        viewHolder.bind(horarios, listener)
    }

    fun updateData(horarios: List<Horarios>) {
        horarioPeliculaList.clear()
        horarioPeliculaList.addAll(horarios)
        notifyDataSetChanged()
    }

    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val binding = HorariosItemBinding.bind(itemView)

        fun bind(horarios: Horarios, listener: OnHorariosPeliculasItemListener) {
            val date = LocalDate.parse(horarios.date)
            val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd - EEEE")
            binding.txtFecha.text = date.format(dateFormat)
            binding.txtHora.text = horarios.time
            binding.txtPrecio.text = horarios.price.toString()+"BS"
            binding.btnComprar.setOnClickListener {
                listener.onHorariosPeliculasItemClick(horarios)
            }
        }

    }

    interface OnHorariosPeliculasItemListener {
        fun onHorariosPeliculasItemClick(horarios: Horarios)
    }
}