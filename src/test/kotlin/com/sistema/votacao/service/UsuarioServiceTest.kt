package com.sistema.votacao.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.sistema.votacao.controller.dto.CpfDTO
import com.sistema.votacao.exception.BusinessException
import com.sistema.votacao.model.Usuario
import com.sistema.votacao.repository.UsuarioRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@RestClientTest(UsuarioServiceTest::class)
@AutoConfigureWebClient(registerRestTemplate=true)
@RunWith(SpringRunner::class)
@ExtendWith(MockitoExtension::class)
class UsuarioServiceTest {

    @Mock
    private val usuarioRepository: UsuarioRepository? = null

    @Autowired
    private val server: MockRestServiceServer? = null

    var usuarioService: UsuarioService? = null

    @BeforeEach
    fun initMock() {
        usuarioService = UsuarioService(usuarioRepository!!)
    }

    @Test
    fun lancaExcecaoAoVerificarCpf(){
        val usuario = Usuario(null, "86081786056", "ana")
        val json = ObjectMapper().writeValueAsString(CpfDTO("UNABLE_TO_VOTE"))

        server!!.expect(requestTo("https://user-info.herokuapp.com/users/03512378013"))
            .andRespond(withSuccess(json, MediaType.APPLICATION_JSON))

        val exception: Throwable = Assertions.catchException { usuarioService!!.cadastraUsuario(usuario) }
        assertThat(exception)
            .isInstanceOf(BusinessException::class.java)
            .hasMessage("CPF inválido, não será possível participar das votações")

        Mockito.verify(usuarioRepository, Mockito.never())?.save(usuario)
    }

    @Test
    fun cadastraUsuarioEVerificaCPF(){
        val usuario = Usuario(null, "00934580022", "ana")
        val json = ObjectMapper().writeValueAsString(CpfDTO("ABLE_TO_VOTE"))
        val response = usuarioService!!.verificaCPF(usuario.cpf)

        server!!.expect(requestTo("https://user-info.herokuapp.com/users/03512378013"))
            .andRespond(withSuccess(json, MediaType.APPLICATION_JSON))

        assertThat(response).isEqualTo(true)
    }

}