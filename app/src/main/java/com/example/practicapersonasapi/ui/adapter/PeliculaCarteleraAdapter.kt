package com.example.practicapersonasapi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicapersonasapi.R
import com.example.practicapersonasapi.databinding.PeliculasItemBinding
import com.example.practicapersonasapi.models.Pelicula
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class PeliculaCarteleraAdapter(private var peliculaCartelertaList: ArrayList<Pelicula>, private val listener: OnPeliculasCarteleraItemListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val rowView = inflater.inflate(R.layout.peliculas_item, parent, false)

        return ViewHolder(rowView)
    }

    override fun getItemCount(): Int {
        return peliculaCartelertaList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pelicula = peliculaCartelertaList[position]
        val viewHolder = holder as ViewHolder
        viewHolder.bind(pelicula, listener)
    }

    fun updateData(pelicula: List<Pelicula>) {
        peliculaCartelertaList.clear()
        peliculaCartelertaList.addAll(pelicula)
        notifyDataSetChanged()
    }

    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val binding = PeliculasItemBinding.bind(itemView)

        fun bind(peliculas: Pelicula, listener: OnPeliculasCarteleraItemListener) {
            Glide.with(itemView)
                .load("http://cineapi.jmacboy.com"+peliculas.posterUrl)
                .placeholder(R.drawable.background)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imgPelicula)

            binding.titlePelicula.text = peliculas.title
            binding.imgPelicula.setOnClickListener {
                listener.onPeliculasCarteleraItemClick(peliculas)
            }
        }

    }

    interface OnPeliculasCarteleraItemListener {
        fun onPeliculasCarteleraItemClick(peliculas: Pelicula)
    }
}