package com.sistema.votacao.controller

import com.sistema.votacao.controller.dto.VotoDTO
import com.sistema.votacao.model.Voto
import com.sistema.votacao.service.VotoService
import ma.glasnost.orika.MapperFacade
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/voto")
class VotoController(
    private val votoService: VotoService,
    private val mapperFacade: MapperFacade
    ) {

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        fun cadastraVoto(@RequestBody votoDTO: VotoDTO): VotoDTO {
            logger.info("Registrando voto")
            val voto = mapperFacade.map(votoDTO, Voto::class.java)
            votoService.cadastraVoto(voto)
            return votoDTO
        }

        companion object{
            private val logger = LoggerFactory.getLogger(this::class.java)
        }
}