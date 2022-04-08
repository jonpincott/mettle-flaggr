package com.example.mettleflaggr.web

import com.example.mettleflaggr.dto.FlagDto
import com.example.mettleflaggr.service.FlagService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Unroll

import static groovy.json.JsonOutput.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [FlagController, ErrorController])
class FlagControllerTest extends Specification {

    @Autowired
    MockMvc mvc

    @SpringBean
    FlagService mockFlagService = Mock()

    @WithMockUser(roles = 'ADMIN')
    @Unroll
    def 'should allow admin user to create a new flag with state #state'() {

        given:
        def request = FlagDto.builder().name("name").state(state).build()
        def response = request.withState(state ?: false)

        and:
        1 * mockFlagService.createFlag(request) >> response
        0 * _

        expect:
        mvc.perform(post('/flag').contentType(APPLICATION_JSON).content(toJson(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json(toJson(response)))

        where:
        state << [true, false, null]
    }

    @WithMockUser(roles = 'ADMIN')
    def 'should get all flags'() {

        given:
        def dtos = (1..3).collect { FlagDto.builder().name('Flag_' + it).state(0 < it % 2).build() }

        and:
        1 * mockFlagService.findAllFlags() >> dtos
        0 * _

        expect:
        mvc.perform(get('/flag'))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json(toJson(dtos)))
    }

}
