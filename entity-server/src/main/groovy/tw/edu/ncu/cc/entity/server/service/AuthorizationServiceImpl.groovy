package tw.edu.ncu.cc.entity.server.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tw.edu.ncu.cc.entity.server.model.EntityAuthorization
import tw.edu.ncu.cc.entity.server.model.EntityAuthorizationRepository
import tw.edu.ncu.cc.entity.server.model.InternetEntity
import tw.edu.ncu.cc.entity.server.model.User

@Service
class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    EntityAuthorizationRepository authorizationRepository

    @Override
    EntityAuthorization create( EntityAuthorization authorization ) {
        authorizationRepository.save( authorization )
    }

    @Override
    EntityAuthorization findByAuthorizeeAndEntity( User authorizee, InternetEntity entity) {
        authorizationRepository.findByAuthorizeeAndEntity( authorizee, entity )
    }

    @Override
    void delete( EntityAuthorization authorization ) {
        authorizationRepository.delete( authorization.id )
    }

}
