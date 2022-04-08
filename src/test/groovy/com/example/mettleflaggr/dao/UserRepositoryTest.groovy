package com.example.mettleflaggr.dao

import com.example.mettleflaggr.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import spock.lang.Specification

@DataJpaTest
class UserRepositoryTest extends Specification {

    @Autowired
    TestEntityManager entityManager

    @Autowired
    UserRepository userRepository

    def 'should find by username'() {

        given:
        entityManager.persist(new User(username: 'username'))

        expect:
        userRepository.findByUsername('username').isPresent()
    }

}
