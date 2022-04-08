package com.example.mettleflaggr

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest
class MettleFlaggrApplicationTest extends Specification {

    @Autowired
    ApplicationContext ctx

    def "context loads"() {
        expect: "the context is created"
        ctx
    }

}
