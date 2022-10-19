package com.sistema.votacao.service

import com.sistema.votacao.exception.BusinessException
import com.sistema.votacao.model.Pauta
import com.sistema.votacao.model.Usuario
import com.sistema.votacao.model.Voto
import com.sistema.votacao.repository.VotoRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import java.time.Month
import java.util.*

@ExtendWith(SpringExtension::class)
class VotoServiceTest {
    @MockBean
    private val repository: VotoRepository? = null

    @MockBean
    private val usuarioService: UsuarioService? = null

    @MockBean
    private val pautaService: PautaService? = null

    var votoService: VotoService? = null

    @BeforeEach
    fun setUp() {
        votoService = VotoService(repository!!, usuarioService!!, pautaService!!)
    }

    @Test
    fun retornaExceptionQuandoUsuarioVotouAnteriormente() {
        val voto = Voto(null, 1, 1, true)
        val pauta = Pauta(1, "pauta1", true, null, listOf(Voto(1, 1, 1, true)), 10, LocalDateTime.of(2022, Month.OCTOBER, 17, 21, 30))
        val usuario = Usuario(1, "11111111111", "ana")

        `when`(pautaService!!.buscaPauta(voto.pautaId)).thenReturn(pauta)
        `when`(usuarioService!!.buscaUsuario(voto.usuarioId)).thenReturn(usuario)

        val exception: Throwable = Assertions.catchException { votoService!!.cadastraVoto(voto) }
        assertThat(exception)
            .isInstanceOf(BusinessException::class.java)
                .hasMessage("Usuário já votou na pauta")

        verify(repository, never())?.save(voto)
    }

}

