package tw.edu.ncu.cc.entity.server.web

import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static tw.edu.ncu.cc.oauth.resource.test.ApiAuthMockMvcRequestPostProcessors.accessToken

class WhoControllerTest extends IntegrationSpecification {

    def targetURL = "/v1/whoami"

    def "user can know who he/she is 1"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL ).with( accessToken().user( "user-uid-1" ).scope( "user.info.basic.read" ) )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.type == "admin"
    }


    def "user can know who he/she is 2"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL ).with( accessToken().user( "user-uid-2" ).scope( "user.info.basic.read" ) )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.type == "common"
    }


    def "user can know who he/she is 3"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL ).with( accessToken().user( "user-uid00" ).scope( "user.info.basic.read" ) )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.type == "undefined"
    }
}
