package tw.edu.ncu.cc.entity.server.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.entity.server.model.User

@Transactional
class UserServiceImplTest extends SpringSpecification {

    @Autowired
    UserService userService

    @Autowired
    EntityService entityService

    def "it can create user"() {
        when:
            def userResponse = userService.create( new User(
                    uid: "123456789",
                    type: User.Type.admin
            ) )
            def userDatabase = userService.findByUID( "123456789" )
        then:
            userResponse.id != null
            userDatabase.uid == "123456789"
            userDatabase.type == User.Type.admin
    }

    def "it can update user"() {
        given:
            def user = userService.findByUID( "user-uid-7" )
        when:
            user.description = "hello"
            def userResponse = userService.update( user )
            def userDatabase = userService.findByUID( "user-uid-7" )
        then:
            userResponse.description == "hello"
            userDatabase.description == "hello"
    }

    def "it can delete user"() {
        given:
            def user = userService.findByUID( "user-uid-7" )
        when:
            userService.delete( user )
        then:
            userService.findByUID( "user-uid-7" ) == null
    }

    def "it can find all users"() {
        expect:
            userService.findAll( new PageRequest( 0, 10 ) ).totalElements == 7
    }

    def "it can find by uid"() {
        expect:
            userService.findByUID( "user-uid-1" ) != null
            userService.findByUID( "user-uid00" ) == null
    }

    def "it can find by authorized entity"() {
        given:
            def entity1 = entityService.findByUUID( "entity-uuid-1" )
            def entity2 = entityService.findByUUID( "entity-uuid-3" )
        expect:
            userService.findByAuthorizedEntity( entity1, new PageRequest( 0, 10 ) ).totalElements == 1
            userService.findByAuthorizedEntity( entity2, new PageRequest( 0, 10 ) ).totalElements == 3

    }
}
