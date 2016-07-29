package tw.edu.ncu.cc.entity.server.operation

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tw.edu.ncu.cc.entity.data.v1.AuthorizationObject
import tw.edu.ncu.cc.entity.server.model.EntityAuthorization

interface AuthorizationOperation {

    Page<EntityAuthorization> index(Pageable pageable, AuthorizationObject authorizationObject )

    EntityAuthorization create( AuthorizationObject authorizationObject, String uid );

    void delete( String authorizeeId, String entity_id );
}
