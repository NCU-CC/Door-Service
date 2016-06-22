package tw.edu.ncu.cc.entity.server.view

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.entity.data.v1.AuthorizationTokenObject
import tw.edu.ncu.cc.entity.server.model.AuthorizationToken


@Component
class AuthorizationToken_AuthorizationTokenObjectConverter implements Converter< AuthorizationToken, AuthorizationTokenObject > {

    @Override
    AuthorizationTokenObject convert(AuthorizationToken source ) {
        AuthorizationTokenObject tokenObject = new AuthorizationTokenObject();
        tokenObject.token = source.token
        tokenObject.createdAt = source.createdAt
        tokenObject.expiredAt = source.expiredAt
        tokenObject
    }

}
