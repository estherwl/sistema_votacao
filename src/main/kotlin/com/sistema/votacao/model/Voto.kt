package com.sistema.votacao.model

import javax.persistence.*

@Entity
@Table(name = "voto")
data class Voto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    val usuarioId: Int,

    val pautaId: Int,

    val voto: Boolean
)
