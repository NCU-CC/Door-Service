package tw.edu.ncu.cc.entity.server.operation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpServerErrorException
import tw.edu.ncu.cc.entity.data.v1.EntityObject
import tw.edu.ncu.cc.entity.server.model.AuthorizationToken
import tw.edu.ncu.cc.entity.server.model.InternetEntity
import tw.edu.ncu.cc.entity.server.model.User
import tw.edu.ncu.cc.entity.server.service.AuthorizationService
import tw.edu.ncu.cc.entity.server.service.AuthorizationTokenService
import tw.edu.ncu.cc.entity.server.service.EntityService
import tw.edu.ncu.cc.entity.server.service.UserService

@Component
@Transactional
class EntityOperationImpl implements EntityOperation {

    @Autowired
    def UserService userService

    @Autowired
    def EntityService entityService

    @Autowired
    def AuthorizationService authorizationService

    @Autowired
    def AuthorizationTokenService tokenService

    @Override
    Page<InternetEntity> index(Pageable pageable) {
        entityService.findAll(pageable)
    }

    @Override
    Page<User> showAuthorities(String uuid, Pageable pageable) {
        def entity = show(uuid)

        return userService.findByAuthorizedEntity(entity, pageable)
    }

    @Override
    InternetEntity show(String uuid) {
        def entity = entityService.findByUUID(uuid)
        if (entity == null) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, "required resource is not found")
        }

        return entity
    }

    @Override
    InternetEntity create(EntityObject entityObject, String creatorUID) {

        def sameIpEntity = entityService.findByIp( entityObject.ip )
        if( sameIpEntity != null ) {
            throw new HttpServerErrorException(HttpStatus.FORBIDDEN, "entity with same ip exists")
        }

        entityService.create(new InternetEntity(
                name: entityObject.name,
                ip: entityObject.ip,
                creator: userService.findByUID(creatorUID)
        ))
    }

    @Override
    InternetEntity update(String uuid, EntityObject entityObject) {
        def sameIpEntity = entityService.findByIp( entityObject.ip )
        if( sameIpEntity != null ) {
            throw new HttpServerErrorException(HttpStatus.FORBIDDEN, "entity with same ip exists")
        }

        def entity = show(uuid)
        entity.name = entityObject.name
        entity.ip = entityObject.ip
        return entityService.update(entity)
    }

    @Override
    void destroy(String uuid) {
        def entity = show(uuid)
        entityService.delete(entity)
    }

    @Override
    AuthorizationToken createToken(String entityUUID, String creatorUID) {

        def entity = show(entityUUID)
        def creator = userService.findByUID( creatorUID )

        def authorization = authorizationService.findByAuthorizeeAndEntity( creator, entity )
        if( authorization == null ) {
            throw new HttpServerErrorException(HttpStatus.FORBIDDEN, "user is not authorized for entity")
        }

        tokenService.create( new AuthorizationToken(
                entity: entity,
                creator: creator
        ) )
    }
}
