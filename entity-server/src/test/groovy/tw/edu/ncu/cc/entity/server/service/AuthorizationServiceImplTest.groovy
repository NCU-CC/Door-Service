package tw.edu.ncu.cc.entity.server.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.entity.server.model.EntityAuthorization

@Transactional
class AuthorizationServiceImplTest extends SpringSpecification {

    @Autowired
    AuthorizationService authorizationService

    @Autowired
    EntityService entityService

    @Autowired
    UserService userService

    def "it can create authorization"() {
        given:
            def user1 = userService.findByUID( "user-uid-1" )
            def user2 = userService.findByUID( "user-uid-6" )
            def entity = entityService.findByUUID( "entity-uuid-1" )
        when:
            authorizationService.create( new EntityAuthorization(
                    authorizer: user1,
                    authorizee: user2,
                    entity: entity
            ) )
        then:
            def authorization = authorizationService.findByAuthorizeeAndEntity( user2, entity )
            authorization.id != null
            authorization.authorizer.id == user1.id
            authorization.authorizee.id == user2.id
            authorization.entity.id == entity.id
            authorization.createdAt != null
    }

    def "it can delete authorization"() {
        given:
            def user = userService.findByUID( "user-uid-1" )
            def entity = entityService.findByUUID( "entity-uuid-1" )
            def authorization = authorizationService.findByAuthorizeeAndEntity( user, entity )
        when:
            authorizationService.delete( authorization )
        then:
            authorizationService.findByAuthorizeeAndEntity( user, entity ) == null
    }
}
