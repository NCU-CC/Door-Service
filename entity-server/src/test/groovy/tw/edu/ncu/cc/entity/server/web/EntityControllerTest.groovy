package tw.edu.ncu.cc.entity.server.web

import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static tw.edu.ncu.cc.oauth.resource.test.ApiAuthMockMvcRequestPostProcessors.accessToken

@Transactional
class EntityControllerTest extends IntegrationSpecification {

    def targetURL = "/v1/entities"

    def adminToken = accessToken().user( "user-uid-1" ).scope( "user.info.basic.read" )
    def commonToken = accessToken().user( "user-uid-2" ).scope( "user.info.basic.read" )

    def "admin can read all entities"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL ).with( adminToken )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.pageMetadata.totalElements == 3
    }

    def "common user cannot read all entities"() {
        expect:
            server().perform(
                    get( targetURL ).with( commonToken )
            ).andExpect(
                    status().isForbidden()
            )
    }


    def "admin can read entity info"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/entity-uuid-2" ).with( adminToken )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.id == "entity-uuid-2"
            response.name == "entity2"
    }


    def "common user cannot read entity info"() {
        expect:
            server().perform(
                    get( targetURL + "/entity-uuid-2" ).with( commonToken )
            ).andExpect(
                    status().isForbidden()
            )
    }

    def "admin can delete entity"() {
        given:
            server().perform(
                    get( targetURL + "/entity-uuid-2" ).with( adminToken )
            ).andExpect(
                    status().isOk()
            )
        when:
            server().perform(
                    delete( targetURL + "/entity-uuid-2" ).with( adminToken )
            ).andExpect(
                    status().isNoContent()
            )
        then:
            server().perform(
                    get( targetURL + "/entity-uuid-2" ).with( adminToken )
            ).andExpect(
                    status().isNotFound()
            )
    }

    def "common user cannot delete entity"() {
        expect:
            server().perform(
                    delete( targetURL + "/entity-uuid-2" ).with( commonToken )
            ).andExpect(
                    status().isForbidden()
            )
    }

    def "admin can create entity"() {
        when:
            def createResponse = JSON(
                    server().perform(
                            post( targetURL ).with( adminToken )
                                    .contentType( MediaType.APPLICATION_JSON )
                                    .content( '{ "name" : "test", "ip" : "1.2.3.4" }' )
                    ).andExpect(
                            status().isCreated()
                    ).andReturn()
            )
        and:
            def readResponse = JSON(
                    server().perform(
                            get( targetURL + "/${createResponse.id}" ).with( adminToken )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            createResponse.name == "test"
            createResponse.ip == "1.2.3.4"
            readResponse.name == "test"
            readResponse.ip == "1.2.3.4"
    }

    def "common user cannot create entity"() {
        expect:
            server().perform(
                    post( targetURL ).with( commonToken )
                            .contentType( MediaType.APPLICATION_JSON )
                            .content( '{ "name" : "test", "ip" : "1.2.3.4" }' )
            ).andExpect(
                    status().isForbidden()
            )
    }

    def "admin can update entity"() {
        when:
            def createResponse = JSON(
                    server().perform(
                            put( targetURL + "/entity-uuid-2" ).with( adminToken )
                                    .contentType( MediaType.APPLICATION_JSON )
                                    .content( '{ "name" : "test", "ip" : "4.3.2.1" }' )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        and:
            def readResponse = JSON(
                    server().perform(
                            get( targetURL + "/entity-uuid-2" ).with( adminToken )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            createResponse.name == "test"
            createResponse.ip == "4.3.2.1"
            readResponse.name == "test"
            readResponse.ip == "4.3.2.1"
    }

    def "common user cannot update entity"() {
        expect:
            server().perform(
                    put( targetURL + "/entity-uuid-2" ).with( commonToken )
                            .contentType( MediaType.APPLICATION_JSON )
                            .content( '{ "name" : "test", "ip" : "4.3.2.1" }' )
            ).andExpect(
                    status().isForbidden()
            )
    }

    def "admin can update entity ip with same ip"() {
        expect:
            server().perform(
                    put( targetURL + "/entity-uuid-2" ).with( adminToken )
                            .contentType( MediaType.APPLICATION_JSON )
                            .content( '{ "name" : "test", "ip" : "0.0.0.2" }' )
            ).andExpect(
                    status().isOk()
            )
    }

    def "admin cannot update entity ip to exist ip"() {
        expect:
            server().perform(
                    put( targetURL + "/entity-uuid-2" ).with( adminToken )
                            .contentType( MediaType.APPLICATION_JSON )
                            .content( '{ "name" : "test", "ip" : "0.0.0.1" }' )
            ).andExpect(
                    status().isForbidden()
            )
    }

    def "admin can read authorizees of specific entity"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/entity-uuid-3/authorizees" ).with( adminToken )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.pageMetadata.totalElements == 3
    }

    def "common user cannot read authorizees of specific entity"() {
        expect:
            server().perform(
                    get( targetURL + "/entity-uuid-3/authorizees" ).with( commonToken )
            ).andExpect(
                    status().isForbidden()
            )
    }

    def "user can create token for specific entity if authorized 1"() {
        when:
            def response = JSON(
                    server().perform(
                            post( targetURL + "/entity-uuid-1/authorization_tokens" ).with( adminToken )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.token != null
    }

    def "user can create token for specific entity if authorized 2"() {
        when:
            def response = JSON(
                    server().perform(
                            post( targetURL + "/entity-uuid-2/authorization_tokens" ).with( commonToken )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.token != null
    }

    def "user cannot create token for specific entity if unauthorized"() {
        expect:
            server().perform(
                    post( targetURL + "/entity-uuid-2/authorization_tokens" ).with( adminToken )
            ).andExpect(
                    status().isForbidden()
            )
    }

}
