package tw.edu.ncu.cc.entity.server.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.entity.server.model.AuthorizationToken

@Transactional
class AuthorizationTokenServiceImplTest extends SpringSpecification {

    @Autowired
    AuthorizationTokenService tokenService

    @Autowired
    EntityService entityService

    @Autowired
    UserService userService

    def "it can create token"() {
        given:
            def creator = userService.findByUID( "user-uid-1" )
            def entity = entityService.findByUUID( "entity-uuid-1" )
        when:
            def tokenResponse = tokenService.create( new AuthorizationToken(
                    creator: creator,
                    entity: entity
            ) )
            def tokenDatabase = tokenService.findUnexpiredByTokenAndEntityIp( tokenResponse.token, entity.ip )
        then:
            tokenResponse.id != null
            tokenDatabase != null
            tokenDatabase.entity.id == entity.id
            tokenDatabase.creator.id == creator.id
    }

    def "it can find token"() {
        expect:
            tokenService.findUnexpiredByTokenAndEntityIp( "12345", "0.0.0.1" ) != null
            tokenService.findUnexpiredByTokenAndEntityIp( "12345", "0.0.0.2" ) != null
            tokenService.findUnexpiredByTokenAndEntityIp( "12345", "0.0.0.3" ) == null
    }

}
