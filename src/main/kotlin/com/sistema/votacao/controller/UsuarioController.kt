package com.sistema.votacao.controller

import com.sistema.votacao.controller.dto.UsuarioDTO
import com.sistema.votacao.model.Usuario
import com.sistema.votacao.service.UsuarioService
import ma.glasnost.orika.MapperFacade
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/usuario")
class UsuarioController(
    private val usuarioService: UsuarioService,
    private val mapperFacade: MapperFacade
    ) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun cadastraUsuario(@RequestBody usuarioDTO: UsuarioDTO): UsuarioDTO {
        logger.info("Criando usuário com cpf {}", usuarioDTO.cpf)
        val usuario = mapperFacade.map(usuarioDTO, Usuario::class.java)
        usuarioService.cadastraUsuario(usuario)
        return usuarioDTO
    }

    companion object{
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}