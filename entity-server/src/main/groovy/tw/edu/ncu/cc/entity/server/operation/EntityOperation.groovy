package tw.edu.ncu.cc.entity.server.operation

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tw.edu.ncu.cc.entity.data.v1.EntityObject
import tw.edu.ncu.cc.entity.server.model.AuthorizationToken
import tw.edu.ncu.cc.entity.server.model.InternetEntity
import tw.edu.ncu.cc.entity.server.model.User

interface EntityOperation {

    Page<InternetEntity> index( Pageable pageable )
    Page<User> showAuthorities(String uuid, Pageable pageable )
    InternetEntity show( String uuid )
    InternetEntity create( EntityObject entityObject, String creatorUID )
    InternetEntity update( String uuid, EntityObject entityObject )
    AuthorizationToken createToken( String entityUUID, String creatorUID )
    void destroy( String uuid )
}
