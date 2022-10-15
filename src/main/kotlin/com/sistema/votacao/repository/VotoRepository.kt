package com.sistema.votacao.repository

import com.sistema.votacao.model.Voto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VotoRepository: JpaRepository<Voto, Int>
