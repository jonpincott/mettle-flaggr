package com.example.mettleflaggr.dto

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import spock.lang.Specification

import static com.example.mettleflaggr.dto.UserDto.builder
import static groovy.json.JsonOutput.toJson
import static org.assertj.core.api.Assertions.assertThat

@JsonTest
class UserDtoTest extends Specification {

    @Autowired
    JacksonTester<UserDto> jackson

    def 'should convert from json'() {

        given:
        def json = '{"username":"user"}'
        def dto = builder().username('user').build()

        expect:
        jackson.parseObject(json) == dto
    }

    def 'should convert to json'() {

        given:
        def dto = builder().username('user').build()
        def json = '{"username":"user"}'

        expect:
        assertThat(jackson.write(dto)).isEqualToJson(json)
    }

    def 'assert jackson format matches groovy format'() {

        given:
        def dto = builder().username('user').build()

        expect:
        assertThat(jackson.write(dto)).isStrictlyEqualToJson(toJson(dto))
    }

}
