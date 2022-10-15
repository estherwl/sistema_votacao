package com.sistema.votacao.configuration

import ma.glasnost.orika.MapperFacade
import ma.glasnost.orika.MapperFactory
import ma.glasnost.orika.impl.DefaultMapperFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MapperConfiguration {

    @Bean
    fun mapperFactory(): MapperFactory {
        val mapperFactory = DefaultMapperFactory.Builder().mapNulls(false).build()
        return mapperFactory
    }

    @Bean
    fun mapperFacade(mapperFactory: MapperFactory): MapperFacade {
        return mapperFactory.mapperFacade
    }

}