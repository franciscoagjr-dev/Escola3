package com.example.escola

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MatriculaAdapter : RecyclerView.Adapter<MatriculaAdapter.VH>() {

    private var lista: List<Matricula> = emptyList()
    private var ultimaPosicao = -1

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val txtDisciplina: TextView = view.findViewById(R.id.txtDisciplina)
        val txtData: TextView = view.findViewById(R.id.txtData)
        val txtSituacao: TextView = view.findViewById(R.id.txtSituacao)
    }

    fun atualizar(nova: List<Matricula>) {
        lista = nova
        ultimaPosicao = -1
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_matricula, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val m = lista[position]
        holder.txtDisciplina.text = m.disciplinaNome
        val partes = m.dataMatricula.take(10).split("-")
        val data = if (partes.size == 3) "${partes[2]}/${partes[1]}/${partes[0]}" else m.dataMatricula
        holder.txtData.text = "📅 Matrícula em $data"
        holder.txtSituacao.text = m.situacao
        val cor = if (m.situacao == "Ativa") R.color.ativa else R.color.trancada
        holder.txtSituacao.background.mutate().setTint(holder.itemView.context.getColor(cor))
        if (position > ultimaPosicao) {
            val anim = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_enter)
            anim.startOffset = (position % 8) * 50L
            holder.itemView.startAnimation(anim)
            ultimaPosicao = position
        }
    }

    override fun getItemCount() = lista.size
}
