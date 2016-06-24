package tw.edu.ncu.cc.entity.server.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.ConversionService
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import tw.edu.ncu.cc.entity.data.v1.AuthorizationObject
import tw.edu.ncu.cc.entity.server.operation.AuthorizationOperation
import tw.edu.ncu.cc.entity.server.validator.AuthorizationCreateValidator

@RestController
@RequestMapping( value = "v1/authorizations" )
public class AuthorizationController extends BaseController {

    @Autowired
    def ConversionService conversionService

    @Autowired
    def AuthorizationOperation authorizationOperation

    @InitBinder
    public static void initBinder(WebDataBinder binder ) {
        binder.addValidators( new AuthorizationCreateValidator() )
    }

    @PreAuthorize( value = "hasRole('admin')" )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    @RequestMapping( method = RequestMethod.DELETE )
    def delete(  @RequestParam( value = "authorizee_id", required = true ) final String authorizeeId,
                 @RequestParam( value = "entity_id", required = true ) final String entity_id, Authentication authentication ) {

        logger.info( "authorization delete, authorizee:{}, entity:{}, operator:{}",
                authorizeeId, entity_id, authentication.name )


        authorizationOperation.delete( authorizeeId, entity_id )
    }

    @PreAuthorize( value = "hasRole('admin')" )
    @ResponseStatus( HttpStatus.CREATED )
    @RequestMapping( method = RequestMethod.POST )
    def create(@Validated @RequestBody final AuthorizationObject authorizationObject, BindingResult bindingResult, Authentication authentication ) {

        logger.info( "authorization create, authorizer:{}, authorizee:{}, entity:{}, operator:{}",
                authentication.name, authorizationObject.authorizeeId, authorizationObject.entityId, authentication.name )

        if( bindingResult.hasErrors() ) {
            throw new BindException( bindingResult )
        }

        def authorization = authorizationOperation.create( authorizationObject, authentication.name )

        conversionService.convert(
                authorization, AuthorizationObject.class
        )
    }

}