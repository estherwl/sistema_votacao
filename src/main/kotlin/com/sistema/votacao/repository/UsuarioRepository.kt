package com.sistema.votacao.repository

import com.sistema.votacao.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository: JpaRepository<Usuario, Int> {
   fun findUsuarioById(id: Int?): Usuario?
}