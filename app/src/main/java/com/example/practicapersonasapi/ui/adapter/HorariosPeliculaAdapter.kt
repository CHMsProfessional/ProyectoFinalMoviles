package com.example.practicapersonasapi.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practicapersonasapi.R
import com.example.practicapersonasapi.databinding.HorariosItemBinding
import com.example.practicapersonasapi.models.GrupoHorarios
import com.example.practicapersonasapi.models.Horario
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class HorariosPeliculaAdapter(private var horarioPeliculaList: ArrayList<GrupoHorarios>, private val listener: OnHorariosPeliculasItemListener) :
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

    fun updateData(horarios: List<GrupoHorarios>) {
        Log.d("HorariosPeliculaDobladaAdapter", "Actualizando datos")
        horarioPeliculaList = horarios as ArrayList<GrupoHorarios>
        Log.d("HorariosPeliculaDobladaAdapter", "Datos actualizados: $horarioPeliculaList")
        notifyDataSetChanged()
    }


    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val binding = HorariosItemBinding.bind(itemView)

        fun bind(grupoHorarios: GrupoHorarios, listener: OnHorariosPeliculasItemListener) {
            val horariosOrdenados = grupoHorarios.horarios.sortedBy { LocalDate.parse(it.date) }
            binding.Container.removeAllViews()
            for (horario in horariosOrdenados) {
                val view = LayoutInflater.from(itemView.context).inflate(R.layout.horarios_subitem, binding.Container, false)

                view.findViewById<TextView>(R.id.txtHora).text = horario.time.dropLast(3)

                binding.Container.addView(view)

                view.findViewById<Button>(R.id.btnComprar).setOnClickListener {
                    listener.onHorariosPeliculasItemClick(horario)
                }
            }

            if (horariosOrdenados.isNotEmpty()) {
                val firstSchedule = horariosOrdenados[0]
                val date = LocalDate.parse(firstSchedule.date)
                val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd - EEEE")
                binding.txtFecha.text = date.format(dateFormat)
            }
        }

    }

    interface OnHorariosPeliculasItemListener {
        fun onHorariosPeliculasItemClick(horario: Horario)
    }
}