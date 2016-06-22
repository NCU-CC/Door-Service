package tw.edu.ncu.cc.entity.server.service

import tw.edu.ncu.cc.entity.server.model.EntityAuthorization
import tw.edu.ncu.cc.entity.server.model.InternetEntity
import tw.edu.ncu.cc.entity.server.model.User

interface AuthorizationService {
    EntityAuthorization create( EntityAuthorization authorization )
    EntityAuthorization findByAuthorizeeAndEntity( User authorizee, InternetEntity entity )
    void delete( EntityAuthorization authorization )
}