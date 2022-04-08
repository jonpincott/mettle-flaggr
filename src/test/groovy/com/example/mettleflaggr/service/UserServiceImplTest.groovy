package com.example.mettleflaggr.service

import com.example.mettleflaggr.dao.UserRepository
import com.example.mettleflaggr.dto.UserDto
import com.example.mettleflaggr.mapper.UserMapper
import com.example.mettleflaggr.model.User
import spock.lang.Specification
import spock.lang.Subject

class UserServiceImplTest extends Specification {

    def mockUserRepository = Mock(UserRepository)
    def userMapper = UserMapper.INSTANCE

    @Subject
    def sut = new UserServiceImpl(mockUserRepository, userMapper)

    def 'should create a user'() {

        given:
        def user = UserDto.builder().username('username').build()

        and:
        1 * mockUserRepository.save(_ as User) >> { User u -> u.setId(1L) }
        0 * _

        expect:
        sut.createUser(user) == user
    }

    def 'should find all usernames'() {

        given:
        def users = (1..3).collect{new User(id: it, username: 'User ' + it)}
        def dtos = users.collect { UserDto.builder().username(it.username).build() }

        and:
        1 * mockUserRepository.findAll() >> users
        0 * _

        expect:
        sut.findAll() == dtos
    }
}
