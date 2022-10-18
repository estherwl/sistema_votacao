package com.sistema.votacao.service

import com.sistema.votacao.controller.dto.CpfDTO
import com.sistema.votacao.exception.BusinessException
import com.sistema.votacao.model.Usuario
import com.sistema.votacao.repository.UsuarioRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.Optional

@Service
class UsuarioService(private val usuarioRepository: UsuarioRepository) {

    fun cadastraUsuario(usuario: Usuario): Usuario {
        if (!verificaCPF(usuario.cpf)) throw BusinessException("CPF inválido, não será possível participar das votações")
        return usuarioRepository.save(usuario)
    }

    private fun verificaCPF(cpf: String): Boolean {
        val client = RestTemplate().getForEntity("https://user-info.herokuapp.com/users/${cpf}", CpfDTO::class.java)
        val response = client.body!!.status

        logger.info(response)
        if (response == "UNABLE_TO_VOTE") return false
        return true
    }

    fun buscaUsuario(id: Int): Optional<Usuario> {
        return usuarioRepository.findById(id).or { throw BusinessException("Usuário não cadastrado") }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}
