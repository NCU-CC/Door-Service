package tw.edu.ncu.cc.entity.server.web

import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static tw.edu.ncu.cc.oauth.resource.test.ApiAuthMockMvcRequestPostProcessors.accessToken

@Transactional
class AuthorizationControllerTest extends IntegrationSpecification {

    def targetURL = "/v1/authorizations"

    def adminToken = accessToken().user( "user-uid-1" ).scope( "user.info.basic.read" )
    def commonToken = accessToken().user( "user-uid-2" ).scope( "user.info.basic.read" )


    def "admin can delete authorization"() {
        when:
            server().perform(
                    delete( targetURL ).with( adminToken )
                            .param( "authorizee_id", "user-uid-1" )
                            .param( "entity_id", "entity-uuid-1" )
                    ).andExpect(
                            status().isNoContent()
                    )
        and:
            def response = JSON(
                    server().perform(
                            get( "/v1/users/user-uid-1/authorized_entities" ).with( adminToken )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.pageMetadata.totalElements == 0
    }

    def "common user cannot delete authorization"() {
        expect:
            server().perform(
                    delete( targetURL ).with( commonToken )
                            .param( "authorizee_id", "user-uid-1" )
                            .param( "entity_id", "entity-uuid-1" )
            ).andExpect(
                    status().isForbidden()
            )
    }

    def "admin can create authorization"() {
        when:
            server().perform(
                    post( targetURL ).with( adminToken )
                            .contentType( MediaType.APPLICATION_JSON )
                            .content( '{ "authorizeeId" : "user-uid-1", "entityId" : "entity-uuid-2" }' )
            ).andExpect(
                    status().isCreated()
            )
        and:
            def response = JSON(
                    server().perform(
                            get( "/v1/users/user-uid-1/authorized_entities" ).with( adminToken )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.pageMetadata.totalElements == 2
    }

    def "common user cannot create authorization"() {
        expect:
            server().perform(
                    post( targetURL ).with( commonToken )
                            .contentType( MediaType.APPLICATION_JSON )
                            .content( '{ "authorizeeId" : "user-uid-1", "entityId" : "entity-uuid-2" }' )
            ).andExpect(
                    status().isForbidden()
            )
    }


}
