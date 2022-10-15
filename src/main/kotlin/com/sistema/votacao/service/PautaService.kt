package com.sistema.votacao.service

import com.sistema.votacao.exception.BusinessException
import com.sistema.votacao.model.Pauta
import com.sistema.votacao.model.Voto
import com.sistema.votacao.repository.PautaRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PautaService(private val pautaRepository: PautaRepository) {

    fun cadastraPauta(titulo: String, encerramentoVotacao: Long): Pauta {
        val pauta = Pauta(null, titulo, true, null, null, encerramentoVotacao, verificaDataEncerramento(encerramentoVotacao))
        return pautaRepository.save(pauta)
    }

    fun buscaPauta(id: Int): Pauta {
        return pautaRepository.findByPautaId(id) ?: throw BusinessException("Pauta não cadastrada")
    }

    fun buscaTodasPautas(): List<Pauta> {
        return  pautaRepository.findAll()
    }

    fun salvaVoto(pauta: Pauta, voto: Voto){
        pautaRepository.save(pauta.apply { votos = this.votos?.plus(voto) } )
    }

    private fun verificaDataEncerramento(encerramentoVotacao: Long): LocalDateTime {
        return LocalDateTime.now().plusMinutes(encerramentoVotacao)
    }

}