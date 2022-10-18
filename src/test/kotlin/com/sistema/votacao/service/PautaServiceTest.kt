package com.sistema.votacao.service

import com.sistema.votacao.exception.BusinessException
import com.sistema.votacao.model.Pauta
import com.sistema.votacao.model.Voto
import com.sistema.votacao.repository.PautaRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import java.time.Month
import java.time.temporal.ChronoUnit

@ExtendWith(MockitoExtension::class)
class PautaServiceTest {

    @Mock
    private val pautaRepository: PautaRepository? = null

    var pautaService: PautaService? = null

    @BeforeEach
    fun initMock() {
        pautaService = PautaService(pautaRepository!!)
    }

    @Test
    fun cadastraPautaTempoPadrao() {
        val pauta = Pauta(null, "pauta1", true, null, null, 1, LocalDateTime.now().plusMinutes(1).truncatedTo(ChronoUnit.SECONDS))

        `when`(pautaRepository!!.save(pauta)).thenReturn(pauta)

        val pautaCadastrada = pautaService!!.cadastraPauta("pauta1", 1)

        assertThat(pautaCadastrada.estaAberta).isEqualTo(true)
        assertThat(pautaCadastrada.titulo).isEqualTo("pauta1")
        assertThat(pautaCadastrada.encerramentoVotacao).isEqualTo(1)
    }

    @Test
    fun EncerraVotacao() {
        val votos = listOf(Voto(1, 1, 1, true), Voto(2, 5, 1, false), Voto(3, 8, 1, true))
        val pauta = Pauta(1, "pauta1", true, null, votos, 20, LocalDateTime.of(2022, Month.OCTOBER, 1, 10, 30))

        `when`(pautaRepository!!.save(pauta)).thenReturn(pauta)

        val exception: Throwable = Assertions.catchException { pautaService!!.verificaVotacaoEstaEncerrada(pauta) }
        assertThat(exception)
            .isInstanceOf(BusinessException::class.java)
            .hasMessage("Pauta encerrada para votação")
    }

}