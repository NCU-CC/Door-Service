package tw.edu.ncu.cc.entity.server.service

import tw.edu.ncu.cc.entity.server.model.AuthorizationToken

interface AuthorizationTokenService {
    AuthorizationToken create( AuthorizationToken authorizationToken )
    AuthorizationToken findUnexpiredByTokenAndEntityIp( String token, String ip )
}