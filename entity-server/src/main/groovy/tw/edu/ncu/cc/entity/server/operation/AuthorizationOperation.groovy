package tw.edu.ncu.cc.entity.server.operation

import tw.edu.ncu.cc.entity.data.v1.AuthorizationObject
import tw.edu.ncu.cc.entity.server.model.EntityAuthorization

interface AuthorizationOperation {

    EntityAuthorization create( AuthorizationObject authorizationObject, String uid );

    void delete( String authorizeeId, String entity_id );
}
