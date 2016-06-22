package tw.edu.ncu.cc.entity.server.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.entity.server.model.InternetEntity

@Transactional
class EntityServiceImplTest extends SpringSpecification {

    @Autowired
    EntityService entityService

    @Autowired
    UserService userService

    def "it can create entity"() {
        given:
            def user = userService.findByUID( "user-uid-1" )
        when:
            def entityResponse = entityService.create( new InternetEntity(
                    creator: user,
                    name: "test",
                    ip: "9.9.9.9"
            ) )
            def entityDatabase = entityService.findByUUID( entityResponse.uuid )
        then:
            entityResponse.id != null
            entityDatabase.name == "test"
            entityDatabase.ip  == "9.9.9.9"
            entityDatabase.creator.id == user.id
    }

    def "it can update entity"() {
        given:
            def entityResponse = entityService.findByUUID( "entity-uuid-1" )
        when:
            entityResponse.name = "newname"
            def entityDatabase = entityService.update( entityResponse )
        then:
            entityService.findByUUID( "entity-uuid-1" ).name == "newname"
            entityDatabase.name == "newname"
    }

    def "it can delete entity"() {
        given:
            def entity = entityService.findByUUID( "entity-uuid-1" )
        when:
            entityService.delete( entity )
        then:
            entityService.findByUUID( "entity-uuid-1" ) == null
    }

    def "it can find all entity"() {
        expect:
            entityService.findAll( new PageRequest( 0, 10 ) ).totalElements == 3
    }

    def "it can find by uuid"() {
        expect:
            entityService.findByUUID( "entity-uuid-1" ) != null
            entityService.findByUUID( "entity-uuid01" ) == null
    }

    def "it can find by ip"() {
        expect:
            entityService.findByIp( "0.0.0.1" ) != null
            entityService.findByIp( "0.0.0.0" ) == null
    }

    def "it can find by creator"() {
        given:
            def admin = userService.findByUID( "user-uid-1" )
        expect:
            entityService.findByCreator( admin, new PageRequest( 0, 10 ) ).totalElements == 3
    }

    def "it can find by authorized user"() {
        given:
            def user = userService.findByUID( "user-uid-1" )
        expect:
            entityService.findAuthorizedByUser( user, new PageRequest( 0, 10 ) ).totalElements == 1
    }

}
