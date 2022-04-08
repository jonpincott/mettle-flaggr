package com.example.mettleflaggr.service

import com.example.mettleflaggr.dao.FlagRepository
import com.example.mettleflaggr.dao.UserFlagRepository
import com.example.mettleflaggr.dao.UserRepository
import com.example.mettleflaggr.dto.UserFlagDto
import com.example.mettleflaggr.mapper.UserFlagMapper
import com.example.mettleflaggr.model.Flag
import com.example.mettleflaggr.model.User
import com.example.mettleflaggr.model.UserFlag
import spock.lang.Specification
import spock.lang.Subject

class UserFlagServiceImplTest extends Specification {

    def mockUserFlagRepository = Mock(UserFlagRepository)
    def mockUserRepository = Mock(UserRepository)
    def mockFlagRepository = Mock(FlagRepository)
    def mapper = UserFlagMapper.INSTANCE

    @Subject
    def sut = new UserFlagServiceImpl(mockUserFlagRepository, mockUserRepository, mockFlagRepository, mapper)

    def 'should create flag status for user'() {

        given:
        def dto = UserFlagDto.builder().user('user').flag('flag').state(true).build()
        def user = new User(id: 1, username: 'user')
        def flag = new Flag(id: 2, name: 'flag', state: true)
        def model = new UserFlag(user: user, flag: flag, state: true)

        and:
        1 * mockUserFlagRepository.findByUserUsernameAndFlagName('user', 'flag') >> Optional.empty()
        1 * mockUserRepository.findByUsername('user') >> Optional.of(user)
        1 * mockFlagRepository.findByName('flag') >> Optional.of(flag)
        1 * mockUserFlagRepository.save(model) >> model
        0 * _

        expect:
        sut.setUserFlagState(dto) == dto
    }

    def 'should set flag status for user'() {

        given:
        def dto = UserFlagDto.builder().user('user').flag('flag').state(true).build()
        def user = new User(id: 1, username: 'user')
        def flag = new Flag(id: 2, name: 'flag', state: true)
        def found = new UserFlag(user: user, flag: flag, state: false)
        def saved = new UserFlag(user: user, flag: flag, state: true)

        and:
        1 * mockUserFlagRepository.findByUserUsernameAndFlagName('user', 'flag') >> Optional.of(found)
        1 * mockUserFlagRepository.save(saved) >> saved
        0 * _

        expect:
        sut.setUserFlagState(dto) == dto
    }

}
