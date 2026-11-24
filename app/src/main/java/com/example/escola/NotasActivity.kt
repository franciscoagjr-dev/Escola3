package com.example.escola

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

class NotasActivity : AppCompatActivity() {

    private val client = OkHttpClient()
    private val adapter = NotaAdapter()
    private lateinit var edtId: EditText
    private lateinit var txtNomeAluno: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)

        edtId = findViewById(R.id.edtId)
        txtNomeAluno = findViewById(R.id.txtNomeAluno)
        val btnBuscar = findViewById<Button>(R.id.btnBuscar)
        val rv = findViewById<RecyclerView>(R.id.rvNotas)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        btnBuscar.setOnClickListener {
            val id = edtId.text.toString().trim()
            if (id.isEmpty()) {
                Toast.makeText(this, "Digite o ID do aluno", Toast.LENGTH_SHORT).show()
            } else {
                buscar(id)
            }
        }
    }

    private fun buscar(id: String) {
        txtNomeAluno.text = "Buscando..."
        val request = Request.Builder()
            .url("https://provapam.runasp.net/api/notas/aluno/$id")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    adapter.atualizar(emptyList())
                    txtNomeAluno.text = "Falha na conexão. Tente novamente."
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val corpo = response.body?.string() ?: ""
                val codigo = response.code
                val sucesso = response.isSuccessful

                if (!sucesso) {
                    runOnUiThread {
                        adapter.atualizar(emptyList())
                        txtNomeAluno.text = if (codigo == 404) "Aluno não encontrado"
                        else "Erro no servidor (código $codigo)"
                    }
                    return
                }

                try {
                    val array = JSONArray(corpo)
                    val lista = mutableListOf<Nota>()
                    var nomeAluno = ""
                    for (i in 0 until array.length()) {
                        val o = array.getJSONObject(i)
                        nomeAluno = o.getString("alunoNome")
                        lista.add(
                            Nota(
                                o.getString("disciplinaNome"),
                                o.getString("bimestre"),
                                o.getDouble("valor"),
                                o.getString("mencao")
                            )
                        )
                    }
                    runOnUiThread {
                        adapter.atualizar(lista)
                        txtNomeAluno.text = if (lista.isEmpty()) "Nenhuma nota encontrada"
                        else "Aluno: $nomeAluno"
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        adapter.atualizar(emptyList())
                        txtNomeAluno.text = "Erro ao ler os dados"
                    }
                }
            }
        })
    }
}
