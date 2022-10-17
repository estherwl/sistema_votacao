package com.sistema.votacao.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sistema.votacao.configuration.MapperConfiguration
import com.sistema.votacao.controller.dto.VotoDTO
import com.sistema.votacao.model.Voto
import com.sistema.votacao.service.VotoService
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

@WebMvcTest(VotoController::class)
@Import(MapperConfiguration::class)
class VotoControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private val votoService: VotoService? = null

    @SpyBean
    private val mapperFacade: MapperFacade? = null

    private val VOTO_URL = "/api/v1/voto"

    @Test
    fun cadastraVoto() {
        val votoDTO = VotoDTO(null, 1, 1, true)
        val json = ObjectMapper().writeValueAsString(votoDTO)

        val request: MockHttpServletRequestBuilder = MockMvcRequestBuilders.post(VOTO_URL)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)

        mockMvc.perform(request)
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(null))
            .andExpect(jsonPath("$.usuarioId").value(1))
            .andExpect(jsonPath("$.pautaId").value(1))
            .andExpect(jsonPath("$.voto").value(true))
    }
}
