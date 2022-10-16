package com.sistema.votacao.controller

import com.sistema.votacao.controller.dto.PautaDTO
import com.sistema.votacao.service.PautaService
import ma.glasnost.orika.MapperFacade
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/pauta")
class PautaController(
    private val pautaService: PautaService,
    private val mapperFacade: MapperFacade
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun cadastraPauta(@RequestHeader titulo: String, @RequestHeader encerramentoVotacao: Long?): PautaDTO {
        logger.info("Criando pauta nomeada: {}", titulo)
        return pautaService.cadastraPauta(titulo, encerramentoVotacao)
            .let { mapperFacade.map(it, PautaDTO::class.java) }
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun buscaPauta(@PathVariable pautaId: Int): PautaDTO {
        logger.info("Buscando pauta com id: {}", pautaId)
        return pautaService.buscaPauta(pautaId)
            .let { mapperFacade.map(it, PautaDTO::class.java) } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun buscaTodasPautas(): List<PautaDTO> {
        logger.info("Buscando pautas")
        return pautaService.buscaTodasPautas()
            .map { mapperFacade.map(it, PautaDTO::class.java) }
    }

    companion object{
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}