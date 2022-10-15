package com.sistema.votacao.controller.dto

import java.time.LocalDateTime

data class PautaDTO(
    val pautaId: Int? = null,

    val titulo: String,

    val estaAberta: Boolean? = null,

    val resultado: ResultadoDTO? = null,

    val votos: List<VotoDTO>? = null,

    val encerramentoVotacao: Long,

    val dataEncerramento: LocalDateTime
)

enum class ResultadoDTO {
    SIM, NAO, EMPATE
}
