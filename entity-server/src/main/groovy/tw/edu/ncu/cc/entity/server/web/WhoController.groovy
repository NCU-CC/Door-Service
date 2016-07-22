package tw.edu.ncu.cc.entity.server.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import tw.edu.ncu.cc.entity.data.v1.WhoObject
import tw.edu.ncu.cc.entity.server.model.User
import tw.edu.ncu.cc.entity.server.service.UserService

@RestController
@RequestMapping( value = "v1/whoami" )
public class WhoController extends BaseController {

    @Autowired
    def UserService userService

    @RequestMapping( method = RequestMethod.GET )
    def whoami( Authentication authentication ) {

        logger.info( "who show, operator:{}", authentication.name)

        def user = userService.findByUID( authentication.name )

        if( user == null ) {
            return new WhoObject( type: "undefined" )
        } else {
            return new WhoObject( type: user.type.name(), uid: user.uid, description: user.description )
        }
    }

}
