package com.example.mettleflaggr.service

import com.example.mettleflaggr.dao.FlagRepository
import com.example.mettleflaggr.dto.FlagDto
import com.example.mettleflaggr.mapper.FlagMapper
import com.example.mettleflaggr.model.Flag
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class FlagServiceImplTest extends Specification {

    def modelMapper = FlagMapper.INSTANCE
    def mockFlagRepository = Mock(FlagRepository)

    @Subject
    def sut = new FlagServiceImpl(modelMapper, mockFlagRepository)

    @Unroll
    def 'should create a #expected flag when state is #state'() {

        given:
        def flagDto = FlagDto.builder().name("Feature 1").state(state).build()

        and:
        1 * mockFlagRepository.save(_ as Flag) >> { Flag f -> f.setId(1L) }
        0 * _

        when:
        def actual = sut.createFlag(flagDto)

        then:
        actual.name == "Feature 1"
        actual.state == expected

        where:
        state || expected
        true  || true
        false || false
        null  || false
    }

    def 'should find all active flags by username'() {

        given:
        def flags = (1..3).collect { new Flag(id: it, name: 'Flag_' + it, state: it % 2) }
        def dtos = flags.collect { FlagDto.builder().name(it.name).state(it.state).build() }

        and:
        1 * mockFlagRepository.findActiveFlagNamesByUsername("username") >> flags
        0 * _

        expect:
        sut.findActiveFlagsByUsername("username") == dtos
    }

    def 'should find all flags'() {

        given:
        def flags = (1..3).collect { new Flag(id: it, name: 'Flag_' + it, state: it % 2) }
        def dtos = flags.collect { FlagDto.builder().name(it.name).state(it.state).build() }

        and:
        1 * mockFlagRepository.findAll() >> flags
        0 * _

        expect:
        sut.findAllFlags() == dtos
    }

}
