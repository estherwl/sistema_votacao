package com.sistema.votacao.controller.dto

data class VotoDTO(
    val id: Int?,

    val usuarioId: Int,

    val pautaId: Int,

    val voto: Boolean
)
