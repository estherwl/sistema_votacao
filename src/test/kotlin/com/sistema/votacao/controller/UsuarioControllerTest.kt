package com.sistema.votacao.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sistema.votacao.configuration.MapperConfiguration
import com.sistema.votacao.controller.dto.UsuarioDTO
import com.sistema.votacao.model.Usuario
import com.sistema.votacao.service.UsuarioService
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

@WebMvcTest(UsuarioController::class)
@Import(MapperConfiguration::class)
class UsuarioControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private val usuarioService: UsuarioService? = null

    @SpyBean
    private val mapperFacade: MapperFacade? = null

    private val USUARIO_URL = "/api/v1/usuario"

    @Test
    fun criaUsuario() {
        val usuarioDTO = UsuarioDTO(null, "11111111111", "ana")
        val usuario = mapperFacade!!.map(usuarioDTO, Usuario::class.java)
        val json = ObjectMapper().writeValueAsString(usuarioDTO)

        `when`(usuarioService!!.cadastraUsuario(usuario)).thenReturn(usuario)

        val request: MockHttpServletRequestBuilder = MockMvcRequestBuilders.post(USUARIO_URL)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)

        mockMvc.perform(request)
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(null))
            .andExpect(jsonPath("$.cpf").value("11111111111"))
            .andExpect(jsonPath("$.nome").value("ana"))
    }
}
