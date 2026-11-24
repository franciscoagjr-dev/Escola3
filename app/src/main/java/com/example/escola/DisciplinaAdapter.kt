package com.example.escola

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DisciplinaAdapter : RecyclerView.Adapter<DisciplinaAdapter.VH>() {

    private var lista: List<Disciplina> = emptyList()
    private var ultimaPosicao = -1

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val txtNome: TextView = view.findViewById(R.id.txtNome)
        val txtProfessor: TextView = view.findViewById(R.id.txtProfessor)
        val txtCarga: TextView = view.findViewById(R.id.txtCarga)
    }

    fun atualizar(nova: List<Disciplina>) {
        lista = nova
        ultimaPosicao = -1
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_disciplina, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val d = lista[position]
        holder.txtNome.text = d.nome
        holder.txtProfessor.text = "👤 ${d.professor}"
        holder.txtCarga.text = "⏱ Carga horária: ${d.cargaHoraria}h"
        if (position > ultimaPosicao) {
            val anim = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_enter)
            anim.startOffset = (position % 8) * 50L
            holder.itemView.startAnimation(anim)
            ultimaPosicao = position
        }
    }

    override fun getItemCount() = lista.size
}
