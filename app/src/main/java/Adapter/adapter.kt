package Adapter

import Interface.cardListener
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week1.R
import Model.Hewan
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AlertDialog
import com.example.week1.databinding.AnimalsbannerBinding
import com.google.android.material.snackbar.Snackbar
import android.widget.TextView


class ListdataRVadapter (val listhewan : ArrayList<Hewan>, val cardListener: cardListener) :
    RecyclerView.Adapter<ListdataRVadapter.viewHolder>() {



    class viewHolder(val itemView: View, val cardListener: cardListener) :
        RecyclerView.ViewHolder(itemView) {

        val binding = AnimalsbannerBinding.bind(itemView)

        fun setData(data: Hewan) {
            binding.textView.text = data.nama
            binding.textView3.text = data.jenis
            binding.textView4.text = "Umur = "+data.umur.toString()

            if (data.imageUri.isNotEmpty()) {
                binding.imageView.setImageURI(Uri.parse(data.imageUri))
            }
            binding.button2.setOnClickListener {
                cardListener.onCardClick(edit = true, adapterPosition)
            }
            binding.button3.setOnClickListener {
                cardListener.onCardClick(edit = false, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.animalsbanner, parent, false)
        return viewHolder(view, cardListener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.setData(listhewan[position])
    }

    override fun getItemCount(): Int {
        return listhewan.size
    }
}