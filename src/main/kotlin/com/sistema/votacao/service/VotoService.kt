package com.sistema.votacao.service

import com.sistema.votacao.exception.BusinessException
import com.sistema.votacao.model.Voto
import com.sistema.votacao.repository.VotoRepository
import org.springframework.stereotype.Service

@Service
class VotoService(
    private val votoRepository: VotoRepository,
    private val usuarioService: UsuarioService,
    private val pautaService: PautaService
) {
    fun cadastraVoto(voto: Voto) {
        val pauta = pautaService.buscaPauta(voto.pautaId)
        val usuario = usuarioService.buscaUsuario(voto.usuarioId)
        val listaVotantesPauta = pauta.votos?.stream()?.map { it.usuarioId }

        pautaService.verificaVotacaoEstaEncerrada(pauta)

        if (usuario.id != null && listaVotantesPauta?.allMatch { it != voto.usuarioId } == true) {
            votoRepository.save(voto)
            pautaService.salvaVoto(pauta, voto)
        } else {
            throw BusinessException("Usuário já votou na pauta")
        }

    }
}
