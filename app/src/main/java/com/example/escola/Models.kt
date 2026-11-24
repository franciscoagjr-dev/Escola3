package com.example.escola

data class Disciplina(
    val id: Int,
    val nome: String,
    val professor: String,
    val cargaHoraria: Int
)

data class Aluno(
    val id: Int,
    val nome: String,
    val rm: String,
    val turma: String
)

data class Nota(
    val disciplinaNome: String,
    val bimestre: String,
    val valor: Double,
    val mencao: String
)

data class Matricula(
    val disciplinaNome: String,
    val dataMatricula: String,
    val situacao: String
)
