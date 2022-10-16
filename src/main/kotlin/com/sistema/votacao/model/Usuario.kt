package com.sistema.votacao.model

import javax.persistence.*

@Entity
@Table(name = "usuario")
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    @Column(unique = true)
    val cpf: String,
    @Column
    val nome: String
)
