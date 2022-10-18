package com.sistema.votacao.service

import com.sistema.votacao.exception.BusinessException
import com.sistema.votacao.model.Pauta
import com.sistema.votacao.model.Resultado
import com.sistema.votacao.model.Voto
import com.sistema.votacao.repository.PautaRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class PautaService(private val pautaRepository: PautaRepository) {
    private val TEMPO_PADRAO: Long = 1

    fun cadastraPauta(titulo: String, encerramentoVotacao: Long?): Pauta {
        val duracaoVotacao = encerramentoVotacao ?: TEMPO_PADRAO
        val pauta = Pauta(null, titulo, true, null, null, duracaoVotacao, verificaDataEncerramento(duracaoVotacao))
        return pautaRepository.save(pauta)
    }

    fun buscaPauta(id: Int): Pauta {
        return pautaRepository.findByPautaId(id) ?: throw BusinessException("Pauta não cadastrada")
    }

    fun buscaTodasPautas(): List<Pauta> {
        return pautaRepository.findAll()
    }

    fun salvaVoto(pauta: Pauta, voto: Voto) {
        pautaRepository.save(pauta.apply { votos = this.votos?.plus(voto) })
    }

    fun verificaVotacaoEstaEncerrada(pauta: Pauta) {
        if (pauta.dataEncerramento.isBefore(LocalDateTime.now())) {
            logger.info("Encerrando votação")
            contaResultado(pauta)
            pautaRepository.save(pauta.apply { estaAberta = false })
            throw BusinessException("Pauta encerrada para votação")
        }
    }

    private fun contaResultado(pauta: Pauta) {
        logger.info("Apurando resultado")

        val votosSim = pauta.votos!!.count { it.voto }
        val votosNao = pauta.votos!!.count { !it.voto }

        when {
            votosNao > votosSim -> pautaRepository.save(pauta.apply { resultado = Resultado.NAO })
            votosNao < votosSim -> pautaRepository.save(pauta.apply { resultado = Resultado.SIM })
            else -> pautaRepository.save(pauta.apply { resultado = Resultado.EMPATE })
        }
    }

    private fun verificaDataEncerramento(encerramentoVotacao: Long): LocalDateTime {
        return LocalDateTime.now().plusMinutes(encerramentoVotacao).truncatedTo(ChronoUnit.SECONDS)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

}
