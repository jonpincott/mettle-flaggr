package com.example.mettleflaggr.web

import com.example.mettleflaggr.dto.FlagDto
import com.example.mettleflaggr.dto.UserDto
import com.example.mettleflaggr.service.FlagService
import com.example.mettleflaggr.service.UserService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static groovy.json.JsonOutput.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [UserController, ErrorController])
class UserControllerTest extends Specification {

    @Autowired
    MockMvc mvc

    @SpringBean
    UserService mockUserService = Mock()

    @SpringBean
    FlagService mockFlagService = Mock()

    @WithMockUser(roles = 'ADMIN')
    def 'should allow admin user to create a new user'() {

        given:
        def user = UserDto.builder().username("User 1").build()

        and:
        1 * mockUserService.createUser(user) >> user
        0 * _

        expect:
        mvc.perform(post('/user').contentType(APPLICATION_JSON).content(toJson(user)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json(toJson(user)))
    }

    @WithMockUser(roles = 'ADMIN')
    def 'should allow admin user to list users'() {

        given:
        def users = (1..3).collect { UserDto.builder().username('User ' + it).build() }

        and:
        1 * mockUserService.findAll() >> users
        0 * _

        expect:
        mvc.perform(get('/user').contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json(toJson(users)))
    }

    @WithMockUser
    def 'should deny access to non-admin user'() {

        given:
        def user = UserDto.builder().username("User 1").build()

        and:
        0 * _

        expect:
        mvc.perform(post('/user').contentType(APPLICATION_JSON).content(toJson(user)))
                .andExpect(status().isForbidden())
    }

    @WithMockUser(username = 'myUser')
    def 'should list all active flags for user'() {

        given:
        def flags = (1..3).collect { FlagDto.builder().name('Flag_' + it).state(true).build() }

        and:
        1 * mockFlagService.findActiveFlagsByUsername('myUser') >> flags
        0 * _

        expect:
        mvc.perform(get("/user/me/flag").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json(toJson(flags)))
    }
}
