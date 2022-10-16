package com.sistema.votacao.service

import com.sistema.votacao.exception.BusinessException
import com.sistema.votacao.model.Pauta
import com.sistema.votacao.model.Resultado
import com.sistema.votacao.model.Voto
import com.sistema.votacao.repository.PautaRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PautaService(private val pautaRepository: PautaRepository) {
    val TEMPO_PADRAO:Long = 1

    fun cadastraPauta(titulo: String, encerramentoVotacao: Long?): Pauta {
        val duracaoVotacao = encerramentoVotacao ?: TEMPO_PADRAO

        val pauta = Pauta(null, titulo, true, null, null, duracaoVotacao, verificaDataEncerramento(
            duracaoVotacao
        ))
        return pautaRepository.save(pauta)
    }

    fun buscaPauta(id: Int): Pauta {
        return pautaRepository.findByPautaId(id) ?: throw BusinessException("Pauta não cadastrada")
    }

    fun buscaTodasPautas(): List<Pauta> {
        return pautaRepository.findAll()
    }

    fun salvaVoto(pauta: Pauta, voto: Voto){
        pautaRepository.save(pauta.apply { votos = this.votos?.plus(voto) })
    }

    fun encerraVotacao(pauta: Pauta) {
//        if (pauta.dataEncerramento.isBefore(LocalDateTime.now())) {
            contaResultado(pauta)
            pautaRepository.save(pauta.apply { estaAberta = false })
//       }
    }

    private fun contaResultado(pauta: Pauta) {
        val votosSim = pauta.votos!!.count { it.voto }
        val votosNao = pauta.votos!!.count { !it.voto }

        when {
            votosNao > votosSim -> pautaRepository.save(pauta.apply { resultado = Resultado.NAO })
            votosNao < votosSim -> pautaRepository.save(pauta.apply { resultado = Resultado.SIM })
            else -> pautaRepository.save(pauta.apply { resultado = Resultado.EMPATE })
        }
    }

    private fun verificaDataEncerramento(encerramentoVotacao: Long): LocalDateTime {
        return LocalDateTime.now().plusMinutes(encerramentoVotacao)
    }

}
