package tw.edu.ncu.cc.entity.server.web

import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static tw.edu.ncu.cc.oauth.resource.test.ApiAuthMockMvcRequestPostProcessors.accessToken

@Transactional
class UserControllerTest extends IntegrationSpecification {

    def targetURL = "/v1/users"

    def adminToken = accessToken().user( "user-uid-1" ).scope( "user.info.basic.read" )
    def commonToken = accessToken().user( "user-uid-2" ).scope( "user.info.basic.read" )

    def "admin can read all users"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL ).with( adminToken )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.pageMetadata.totalElements == 7
    }

    def "common user cannot read all users"() {
        expect:
            server().perform(
                    get( targetURL ).with( commonToken )
            ).andExpect(
                    status().isForbidden()
            )
    }


    def "admin can read user info"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/user-uid-2" ).with( adminToken )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.id == "user-uid-2"
            response.type == "common"
    }


    def "common user cannot read user info"() {
        expect:
            server().perform(
                    get( targetURL + "/user-uid-2" ).with( commonToken )
            ).andExpect(
                    status().isForbidden()
            )
    }

    def "admin can delete user"() {
        given:
            server().perform(
                    get( targetURL + "/user-uid-2" ).with( adminToken )
            ).andExpect(
                    status().isOk()
            )
        when:
            server().perform(
                    delete( targetURL + "/user-uid-2" ).with( adminToken )
            ).andExpect(
                    status().isNoContent()
            )
        then:
            server().perform(
                    get( targetURL + "/user-uid-2" ).with( adminToken )
            ).andExpect(
                    status().isNotFound()
            )
    }

    def "common user cannot delete user"() {
        expect:
            server().perform(
                    delete( targetURL + "/user-uid-2" ).with( commonToken )
            ).andExpect(
                    status().isForbidden()
            )
    }

    def "admin can create user"() {
        when:
            def createResponse = JSON(
                    server().perform(
                            post( targetURL ).with( adminToken )
                                    .contentType( MediaType.APPLICATION_JSON )
                                    .content( '{ "id" : "user0", "type" : "common" }' )
                    ).andExpect(
                            status().isCreated()
                    ).andReturn()
            )
        and:
            def readResponse = JSON(
                    server().perform(
                            get( targetURL + "/user0" ).with( adminToken )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            createResponse.id == "user0"
            readResponse.id == "user0"
    }

    def "common user cannot create user"() {
        expect:
            server().perform(
                    post( targetURL ).with( commonToken )
                            .contentType( MediaType.APPLICATION_JSON )
                            .content( '{ "id" : "user0", "type" : "common" }' )
            ).andExpect(
                    status().isForbidden()
            )
    }

    def "admin can update user"() {
        when:
            def createResponse = JSON(
                    server().perform(
                            put( targetURL + "/user-uid-2" ).with( adminToken )
                                    .contentType( MediaType.APPLICATION_JSON )
                                    .content( '{ "type" : "common", "description" : "gg" }' )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        and:
            def readResponse = JSON(
                    server().perform(
                            get( targetURL + "/user-uid-2" ).with( adminToken )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            createResponse.description == "gg"
            readResponse.description == "gg"
    }

    def "common user cannot update user"() {
        expect:
            server().perform(
                    put( targetURL + "/user-uid-2" ).with( commonToken )
                            .contentType( MediaType.APPLICATION_JSON )
                            .content( '{ "type" : "common", "description" : "gg" }' )
            ).andExpect(
                    status().isForbidden()
            )
    }

    def "user can read his/her authorized entities 1"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/user-uid-1/entities?authorized=true" ).with( adminToken )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.pageMetadata.totalElements == 1
    }

    def "user can read his/her authorized entities 2"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/user-uid-2/entities?authorized=true" ).with( commonToken )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.pageMetadata.totalElements == 1
    }

    def "admin user can read other's authorized entities 2"() {
        expect:
            server().perform(
                    get( targetURL + "/user-uid-3/entities?authorized=true" ).with( adminToken )
            ).andExpect(
                    status().isOk()
            )
    }

    def "common user cannot read other's authorized entities 2"() {
        expect:
            server().perform(
                    get( targetURL + "/user-uid-3/entities?authorized=true" ).with( commonToken )
            ).andExpect(
                    status().isForbidden()
            )
    }

    def "user can read his/her unauthorized entities 1"() {
        when:
        def response = JSON(
                server().perform(
                        get( targetURL + "/user-uid-1/entities?authorized=false" ).with( adminToken )
                ).andExpect(
                        status().isOk()
                ).andReturn()
        )
        then:
        response.pageMetadata.totalElements == 4
    }


    def "user can read all entities with is authorized"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/user-uid-1/entities" ).with( adminToken )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.pageMetadata.totalElements == 5
    }

    def "admin user can read other's all entities with is authorized 2"() {
        expect:
            server().perform(
                    get( targetURL + "/user-uid-3/entities" ).with( adminToken )
            ).andExpect(
                    status().isOk()
            )
    }

    def "common user cannot read other's all entities with is authorized 2"() {
        expect:
            server().perform(
                    get( targetURL + "/user-uid-3/entities" ).with( commonToken )
            ).andExpect(
                    status().isForbidden()
            )
    }

}
