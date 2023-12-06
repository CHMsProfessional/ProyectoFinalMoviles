package com.example.practicapersonasapi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicapersonasapi.R
import com.example.practicapersonasapi.databinding.ProductosItemBinding
import com.example.practicapersonasapi.models.Productos

class ProductoListAdapter(private var productoList: ArrayList<Productos>, private val listener: OnProductosItemListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val rowView = inflater.inflate(R.layout.productos_item, parent, false)

        return ViewHolder(rowView)
    }

    override fun getItemCount(): Int {
        return productoList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val productos = productoList[position]
        val viewHolder = holder as ViewHolder
        viewHolder.bind(productos, listener)
    }

    fun updateData(productos: List<Productos>) {
        productoList.clear()
        productoList.addAll(productos)
        notifyDataSetChanged()
    }

    class ViewHolder(
        itemView: View

    ) : RecyclerView.ViewHolder(itemView) {
        private val binding = ProductosItemBinding.bind(itemView)

        fun bind(productos: Productos, listener: OnProductosItemListener) {
            binding.lblNombre.setText("Nombre: "+productos.nombre)
            binding.lblDescripcion.setText("Descripcion: "+productos.descripcion)
            binding.lblPrecio.setText("Precio Actual: "+productos.precio_actual.toString())
            binding.btnDeleteProducto.setOnClickListener {
                listener.onProductosItemDelete(productos)
            }
            binding.root.setOnClickListener {
                listener.onProductosItemClick(productos)
            }
        }


    }

    interface OnProductosItemListener {
        fun onProductosItemClick(productos: Productos)
        fun onProductosItemDelete(productos: Productos)
    }
}