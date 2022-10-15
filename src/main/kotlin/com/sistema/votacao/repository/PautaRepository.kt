package com.sistema.votacao.repository

import com.sistema.votacao.model.Pauta
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PautaRepository: JpaRepository<Pauta, Int> {
    fun findByPautaId(id: Int?): Pauta?
}
