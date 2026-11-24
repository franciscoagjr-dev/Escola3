package com.example.escola

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlunoAdapter : RecyclerView.Adapter<AlunoAdapter.VH>() {

    private var lista: List<Aluno> = emptyList()
    private var ultimaPosicao = -1
    private val cores = intArrayOf(
        0xFF1565C0.toInt(),
        0xFF00897B.toInt(),
        0xFFEF6C00.toInt(),
        0xFF6A1B9A.toInt(),
        0xFFC62828.toInt(),
        0xFF2E7D32.toInt()
    )

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val txtInicial: TextView = view.findViewById(R.id.txtInicial)
        val txtId: TextView = view.findViewById(R.id.txtId)
        val txtNome: TextView = view.findViewById(R.id.txtNome)
        val txtRm: TextView = view.findViewById(R.id.txtRm)
        val txtTurma: TextView = view.findViewById(R.id.txtTurma)
    }

    fun atualizar(nova: List<Aluno>) {
        lista = nova
        ultimaPosicao = -1
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_aluno, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val a = lista[position]
        holder.txtInicial.text = a.nome.take(1).uppercase()
        holder.txtInicial.background.mutate().setTint(cores[a.id % cores.size])
        holder.txtNome.text = a.nome
        holder.txtId.text = "ID ${a.id}"
        holder.txtRm.text = "RM ${a.rm}"
        holder.txtTurma.text = "🏫 ${a.turma}"
        if (position > ultimaPosicao) {
            val anim = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_enter)
            anim.startOffset = (position % 8) * 50L
            holder.itemView.startAnimation(anim)
            ultimaPosicao = position
        }
    }

    override fun getItemCount() = lista.size
}
