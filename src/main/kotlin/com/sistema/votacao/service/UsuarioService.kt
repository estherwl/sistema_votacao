package com.sistema.votacao.service

import com.sistema.votacao.exception.BusinessException
import com.sistema.votacao.model.Usuario
import com.sistema.votacao.repository.UsuarioRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UsuarioService(private val usuarioRepository: UsuarioRepository) {

    fun cadastraUsuario(usuario: Usuario): Usuario {
        return usuarioRepository.save(usuario)
    }

    fun buscaUsuario(id: Int): Optional<Usuario> {
        return usuarioRepository.findById(id).or { throw BusinessException("Usuário não cadastrado") }
    }

}
