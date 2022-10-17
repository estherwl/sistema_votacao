package com.sistema.votacao.controller

import com.sistema.votacao.configuration.MapperConfiguration
import com.sistema.votacao.model.Pauta
import com.sistema.votacao.service.PautaService
import ma.glasnost.orika.MapperFacade
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@WebMvcTest(PautaController::class)
@Import(MapperConfiguration::class)
class PautaControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private val pautaService: PautaService? = null

    @SpyBean
    private val mapperFacade: MapperFacade? = null

    private val PAUTA_URL = "/api/v1/pauta"

    @Test
    fun criaPauta() {
        val titulo = "Pauta 1"
        val encerramentoVotacao: Long = 15
        val pauta = Pauta(null, titulo, true, null, null, encerramentoVotacao, LocalDateTime.now())

        `when`(pautaService!!.cadastraPauta(titulo, encerramentoVotacao)).thenReturn(pauta)

        val request: MockHttpServletRequestBuilder = MockMvcRequestBuilders.post(PAUTA_URL)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("titulo", titulo)
            .header("encerramentoVotacao", encerramentoVotacao.toString())

        mockMvc.perform(request)
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.pautaId").value(null))
            .andExpect(jsonPath("$.titulo").value("Pauta 1"))
            .andExpect(jsonPath("$.estaAberta").value(true))
            .andExpect(jsonPath("$.resultado").value(null))
            .andExpect(jsonPath("$.votos").value(null))
            .andExpect(jsonPath("$.encerramentoVotacao").value(15))
    }

    @Test
    fun buscaPautaPorId() {
        val pautaId = 1
        val pauta = Pauta(pautaId, "pauta1", true, null, null, 1, LocalDateTime.now())

        `when`(pautaService!!.buscaPauta(pautaId)).thenReturn(pauta)

        val request: MockHttpServletRequestBuilder = MockMvcRequestBuilders.get("$PAUTA_URL/{pautaId}", pautaId)
            .accept(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.pautaId").value(pautaId))
            .andExpect(jsonPath("$.titulo").value("pauta1"))
            .andExpect(jsonPath("$.estaAberta").value(true))
            .andExpect(jsonPath("$.resultado").value(null))
            .andExpect(jsonPath("$.votos").value(null))
            .andExpect(jsonPath("$.encerramentoVotacao").value(1))
    }

    @Test
    fun buscaPautas() {
        val pauta1 = Pauta(1, "pauta1", true, null, null, 1, LocalDateTime.now())
        val pauta2 = Pauta(2, "pauta2", true, null, null, 1, LocalDateTime.now())

        `when`(pautaService!!.buscaTodasPautas()).thenReturn(listOf(pauta1, pauta2))

        val request: MockHttpServletRequestBuilder = MockMvcRequestBuilders.get(PAUTA_URL)
            .accept(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andExpect(status().isOk)
    }
}
