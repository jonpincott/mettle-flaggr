package com.example.mettleflaggr.dao

import com.example.mettleflaggr.model.Flag
import com.example.mettleflaggr.model.User
import com.example.mettleflaggr.model.UserFlag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import spock.lang.Specification

@DataJpaTest
class UserFlagRepositoryTest extends Specification {

    @Autowired
    TestEntityManager entityManager

    @Autowired
    UserFlagRepository userFlagRepository

    def 'should find by username and flag name'() {

        given:
        def user = entityManager.persist(new User(username: 'user'))
        def flag = entityManager.persist(new Flag(name: 'flag', state: false))
        entityManager.persist(new UserFlag(user: user, flag: flag, state: true))

        expect:
        userFlagRepository.findByUserUsernameAndFlagName('user', 'flag').isPresent()
    }

}
