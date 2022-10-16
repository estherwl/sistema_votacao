package com.sistema.votacao.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "pauta")
data class Pauta(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val pautaId: Int?,

    var titulo: String,

    var estaAberta: Boolean?,

    var resultado: Resultado?,

    @OneToMany
    var votos: List<Voto>?,

    val encerramentoVotacao: Long,

    val dataEncerramento: LocalDateTime
)

enum class Resultado {
    SIM, NAO, EMPATE
}
