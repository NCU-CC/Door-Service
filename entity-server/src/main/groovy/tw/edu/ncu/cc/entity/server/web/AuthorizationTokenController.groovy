package tw.edu.ncu.cc.entity.server.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import tw.edu.ncu.cc.entity.data.v1.WhoObject
import tw.edu.ncu.cc.entity.server.service.AuthorizationTokenService

import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping( value = "v1/authorization_tokens" )
public class AuthorizationTokenController extends BaseController {

    @Autowired
    def AuthorizationTokenService authorizationTokenService

    @RequestMapping( value = "{token}", method = RequestMethod.GET )
    def show(  @PathVariable( "token" ) final String token, HttpServletRequest request ) {

        def authorizationToken = authorizationTokenService.findUnexpiredByTokenAndEntityIp( token, request.remoteAddr )

        if( authorizationToken == null ) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND )
        } else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT )
        }
    }

}