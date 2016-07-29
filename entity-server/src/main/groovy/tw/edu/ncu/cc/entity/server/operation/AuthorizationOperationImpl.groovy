package tw.edu.ncu.cc.entity.server.operation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpServerErrorException
import tw.edu.ncu.cc.entity.data.v1.AuthorizationObject
import tw.edu.ncu.cc.entity.server.model.EntityAuthorization
import tw.edu.ncu.cc.entity.server.service.AuthorizationService
import tw.edu.ncu.cc.entity.server.service.EntityService
import tw.edu.ncu.cc.entity.server.service.UserService

@Component
@Transactional
class AuthorizationOperationImpl implements AuthorizationOperation {

    @Autowired
    def UserService userService

    @Autowired
    def EntityService entityService

    @Autowired
    def AuthorizationService authorizationService

    @Override
    Page<EntityAuthorization> index(Pageable pageable, AuthorizationObject authorizationObject) {

        def authorizee = userService.findByUID( authorizationObject.authorizeeId )

        if( authorizee == null ) {
            throw new HttpServerErrorException( HttpStatus.NOT_FOUND, "authorizee is not found" )
        }

        def entity = entityService.findByUUID( authorizationObject.entityId )

        if( entity == null ) {
            throw new HttpServerErrorException( HttpStatus.NOT_FOUND, "entity is not found" )
        }

        def authorization = authorizationService.findByAuthorizeeAndEntity( authorizee, entity )

        if( authorization != null ) {
            return new PageImpl<EntityAuthorization>( Arrays.asList( authorization ), pageable, 1 )
        }

        return new PageImpl<EntityAuthorization>( [], pageable, 0 )
    }

    @Override
    EntityAuthorization create( AuthorizationObject authorizationObject, String uid ) {
        def authorizee = userService.findByUID( authorizationObject.authorizeeId )

        if( authorizee == null ) {
            throw new HttpServerErrorException( HttpStatus.NOT_FOUND, "authorizee is not found" )
        }

        def entity = entityService.findByUUID( authorizationObject.entityId )

        if( entity == null ) {
            throw new HttpServerErrorException( HttpStatus.NOT_FOUND, "entity is not found" )
        }

        if( authorizationService.findByAuthorizeeAndEntity( authorizee, entity ) != null ) {
            throw new HttpServerErrorException( HttpStatus.FORBIDDEN, "user has already been authorized" )
        }

        return authorizationService.create( new EntityAuthorization(
                authorizer: userService.findByUID( uid ),
                authorizee: authorizee,
                entity: entity
        ) )
    }

    @Override
    void delete( String authorizeeId, String entity_id ) {

        def authorizee = userService.findByUID( authorizeeId )

        if( authorizee == null ) {
            throw new HttpServerErrorException( HttpStatus.NOT_FOUND, "authorizee is not found" )
        }

        def entity = entityService.findByUUID( entity_id )

        if( entity == null ) {
            throw new HttpServerErrorException( HttpStatus.NOT_FOUND, "entity is not found" )
        }

        def authorization = authorizationService.findByAuthorizeeAndEntity( authorizee, entity )

        if( authorization == null ) {
            throw new HttpServerErrorException( HttpStatus.NOT_FOUND, "authorization is not found" )
        }

        authorizationService.delete( authorization )
    }

}
