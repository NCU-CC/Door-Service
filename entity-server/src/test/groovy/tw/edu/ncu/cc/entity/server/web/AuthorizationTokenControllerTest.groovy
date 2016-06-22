package tw.edu.ncu.cc.entity.server.web

import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.web.servlet.request.RequestPostProcessor
import org.springframework.transaction.annotation.Transactional
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Transactional
class AuthorizationTokenControllerTest extends IntegrationSpecification {

    def targetURL = "/v1/authorization_tokens"

    def "user can create token for specific entity if authorized"() {
        expect:
            server().perform(
                    get( targetURL + "/12345" ).with( remoteAddr( "0.0.0.1" ) )
            ).andExpect(
                    status().isNoContent()
            )
            server().perform(
                    get( targetURL + "/12345" ).with( remoteAddr( "0.0.0.2" ) )
            ).andExpect(
                    status().isNoContent()
            )
    }

    def "user cannot create token for specific entity if unauthorized"() {
        expect:
            server().perform(
                    get( targetURL + "/12345" ).with( remoteAddr( "x.x.x.x" ) )
            ).andExpect(
                    status().isNotFound()
            )
            server().perform(
                    get( targetURL + "/12345" ).with( remoteAddr( "0.0.0.4" ) )
            ).andExpect(
                    status().isNotFound()
            )
    }


    RequestPostProcessor remoteAddr( String remoteAddr ) {
        new RequestPostProcessor() {
            @Override
            MockHttpServletRequest postProcessRequest( MockHttpServletRequest request ) {
                request.setRemoteAddr( remoteAddr )
                request
            }
        }
    }

}
