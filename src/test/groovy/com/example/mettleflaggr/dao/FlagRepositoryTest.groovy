package com.example.mettleflaggr.dao

import com.example.mettleflaggr.model.Flag
import com.example.mettleflaggr.model.User
import com.example.mettleflaggr.model.UserFlag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.dao.DataIntegrityViolationException
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

@DataJpaTest
class FlagRepositoryTest extends Specification {

    @Autowired
    TestEntityManager entityManager

    @Autowired
    FlagRepository flagRepository

    def 'should create a flag'() {

        given:
        def flag = new Flag(name: 'name', state: true)

        expect:
        flagRepository.save(flag).id != null
    }

    def 'should find by flag name'() {

        given:
        entityManager.persist(new Flag(name: 'name', state: true))

        expect:
        flagRepository.findByName('name').isPresent()
    }

    def 'should find all flags'() {

        given:
        def flags = (1..3).collect { entityManager.persist(new Flag(name: 'Flag_' + it, state: it % 2)) }

        expect:
        assertThat(flagRepository.findAll()).containsExactlyInAnyOrderElementsOf(flags)
    }

    def 'should enforce uniqueness of flag names'() {

        when:
        flagRepository.save(new Flag(name: 'name', state: true))
        flagRepository.save(new Flag(name: 'name', state: true))
        flagRepository.flush()

        then:
        thrown(DataIntegrityViolationException)
    }

    def 'should find all active global flag names'() {

        given:
        def flag1 = entityManager.persist(new Flag(name: 'False 1', state: false))
        def flag2 = entityManager.persist(new Flag(name: 'True 1', state: true))

        when:
        def flags = flagRepository.findActiveFlagNamesByUsername('myUser')

        then:
        assertThat(flags).containsExactlyInAnyOrder(flag2)
    }

    def 'should find all active user overrides'() {

        given:
        def flag = entityManager.persist(new Flag(name: 'flag', state: false))
        def user = entityManager.persist(new User(username: 'myUser'))
        entityManager.persist(new UserFlag(flag: flag, user: user, state: true))

        when:
        def flags = flagRepository.findActiveFlagNamesByUsername('myUser')

        then:
        assertThat(flags).containsExactlyInAnyOrder(flag)
    }

    def 'should not return overridden global defaults for user'() {

        given:
        def flag = entityManager.persist(new Flag(name: 'flag', state: true))
        def user = entityManager.persist(new User(username: 'myUser'))
        entityManager.persist(new UserFlag(flag: flag, user: user, state: false))

        when:
        def flags = flagRepository.findActiveFlagNamesByUsername('myUser')

        then:
        assertThat(flags).isEmpty()
    }

    def 'should not return overridden global defaults for other users'() {

        given:
        def flag = entityManager.persist(new Flag(name: 'flag', state: true))
        def user = entityManager.persist(new User(username: 'otherUser'))
        entityManager.persist(new UserFlag(flag: flag, user: user, state: false))

        when:
        def flags = flagRepository.findActiveFlagNamesByUsername('myUser')

        then:
        assertThat(flags).isEmpty()
    }

}
