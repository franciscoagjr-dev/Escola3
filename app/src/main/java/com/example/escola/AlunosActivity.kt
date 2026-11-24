package com.example.escola

import android.os.Bundle
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

class AlunosActivity : AppCompatActivity() {

    private val client = OkHttpClient()
    private val adapter = AlunoAdapter()
    private lateinit var txtStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alunos)

        txtStatus = findViewById(R.id.txtStatus)
        val rv = findViewById<RecyclerView>(R.id.rvAlunos)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        carregar()
    }

    private fun carregar() {
        txtStatus.text = "Carregando..."
        val request = Request.Builder()
            .url("https://provapam.runasp.net/api/alunos")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    txtStatus.text = "Falha na conexão. Tente novamente."
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val corpo = response.body?.string() ?: ""
                val sucesso = response.isSuccessful
                try {
                    if (!sucesso) throw Exception("HTTP ${response.code}")
                    val array = JSONArray(corpo)
                    val lista = mutableListOf<Aluno>()
                    for (i in 0 until array.length()) {
                        val o = array.getJSONObject(i)
                        lista.add(
                            Aluno(
                                o.getInt("id"),
                                o.getString("nome"),
                                o.getString("rm"),
                                o.getString("turma")
                            )
                        )
                    }
                    runOnUiThread {
                        adapter.atualizar(lista)
                        txtStatus.text = "${lista.size} alunos"
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        txtStatus.text = "Erro ao carregar alunos"
                        Toast.makeText(this@AlunosActivity, "Erro: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}
