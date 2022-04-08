package com.example.mettleflaggr.web

import com.example.mettleflaggr.dto.UserFlagDto
import com.example.mettleflaggr.service.UserFlagService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Specification

import static groovy.json.JsonOutput.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [UserFlagController, ErrorController])
class UserFlagControllerTest extends Specification {

    @Autowired
    MockMvc mvc

    @SpringBean
    UserFlagService mockUserFlagService = Mock()

    @WithMockUser(roles = 'ADMIN')
    def 'should allow admin user to assign a flag to a user'() {

        given:
        def dto = UserFlagDto.builder().user('user').flag('flag').state(true).build()

        and:
        1 * mockUserFlagService.setUserFlagState(dto) >> dto
        0 * _

        expect:
        mvc.perform(put('/userflag').contentType(APPLICATION_JSON).content(toJson(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json(toJson(dto)))
    }

    @WithMockUser(roles = 'ADMIN')
    def 'should handle validation errors'() {

        given:
        def dto = UserFlagDto.builder().user('user').flag('flag').build()

        and:
        0 * _

        expect:
        mvc.perform(put('/userflag').contentType(APPLICATION_JSON).content(toJson(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json(toJson([state: 'must not be null'])))
    }

}
