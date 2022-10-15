package com.sistema.votacao.service

import com.sistema.votacao.model.Voto
import com.sistema.votacao.repository.VotoRepository
import org.springframework.stereotype.Service

@Service
class VotoService(
    private val votoRepository: VotoRepository,
    private val usuarioService: UsuarioService,
    private val pautaService: PautaService
) {
    fun cadastraVoto(voto: Voto): Voto {
        val pauta = pautaService.buscaPauta(voto.pautaId)
        val usuario = usuarioService.buscaUsuario(voto.usuarioId)
        val listaVotantesPauta = pauta.votos?.stream()?.map { it.usuarioId }

        if (pauta.estaAberta == true) {
            //verifica se usuário foi cadastrado e se já votou na pauta
            if (usuario.isPresent && listaVotantesPauta?.allMatch { it != voto.usuarioId } == true){
                votoRepository.save(voto)
                pautaService.salvaVoto(pauta, voto)
            }
        }
         return voto
    }


}
