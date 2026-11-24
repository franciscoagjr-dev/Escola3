package com.example.escola

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class NotaAdapter : RecyclerView.Adapter<NotaAdapter.VH>() {

    private var lista: List<Nota> = emptyList()
    private var ultimaPosicao = -1

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val txtDisciplina: TextView = view.findViewById(R.id.txtDisciplina)
        val txtBimestre: TextView = view.findViewById(R.id.txtBimestre)
        val txtNota: TextView = view.findViewById(R.id.txtNota)
        val txtMencao: TextView = view.findViewById(R.id.txtMencao)
    }

    fun atualizar(nova: List<Nota>) {
        lista = nova
        ultimaPosicao = -1
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nota, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val n = lista[position]
        holder.txtDisciplina.text = n.disciplinaNome
        holder.txtBimestre.text = n.bimestre
        holder.txtNota.text = String.format(Locale("pt", "BR"), "%.1f", n.valor)
        holder.txtMencao.text = n.mencao
        val cor = when (n.mencao) {
            "MB" -> R.color.mencao_mb
            "B" -> R.color.mencao_b
            "R" -> R.color.mencao_r
            else -> R.color.mencao_i
        }
        holder.txtMencao.background.mutate().setTint(holder.itemView.context.getColor(cor))
        if (position > ultimaPosicao) {
            val anim = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_enter)
            anim.startOffset = (position % 8) * 50L
            holder.itemView.startAnimation(anim)
            ultimaPosicao = position
        }
    }

    override fun getItemCount() = lista.size
}
