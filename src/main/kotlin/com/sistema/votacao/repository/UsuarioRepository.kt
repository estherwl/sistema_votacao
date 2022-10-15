package com.sistema.votacao.repository

import com.sistema.votacao.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UsuarioRepository: JpaRepository<Usuario, Int> {

    fun findByCpf(cpf: String?): Optional<Usuario>
}