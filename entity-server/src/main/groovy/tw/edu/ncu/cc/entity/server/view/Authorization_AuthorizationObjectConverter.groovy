package tw.edu.ncu.cc.entity.server.view

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.entity.data.v1.AuthorizationObject
import tw.edu.ncu.cc.entity.server.model.EntityAuthorization


@Component
class Authorization_AuthorizationObjectConverter implements Converter< EntityAuthorization, AuthorizationObject > {

    @Override
    AuthorizationObject convert( EntityAuthorization source ) {
        AuthorizationObject authorizationObject = new AuthorizationObject();
        authorizationObject.authorizeeId = source.authorizee.uid
        authorizationObject.authorizerId = source.authorizer.uid
        authorizationObject.entityId = source.entity.uuid
        authorizationObject.createdAt = source.createdAt
        authorizationObject
    }

}
