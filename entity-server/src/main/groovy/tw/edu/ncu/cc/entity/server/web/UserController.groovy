package tw.edu.ncu.cc.entity.server.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.ConversionService
import org.springframework.core.convert.TypeDescriptor
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpServerErrorException
import tw.edu.ncu.cc.entity.data.v1.EntityAuthorizedObject
import tw.edu.ncu.cc.entity.data.v1.EntityObject
import tw.edu.ncu.cc.entity.data.v1.UserObject
import tw.edu.ncu.cc.entity.server.model.InternetEntity
import tw.edu.ncu.cc.entity.server.model.User
import tw.edu.ncu.cc.entity.server.model.User.Type
import tw.edu.ncu.cc.entity.server.operation.UserOperation
import tw.edu.ncu.cc.entity.server.service.EntityService
import tw.edu.ncu.cc.entity.server.service.UserService
import tw.edu.ncu.cc.entity.server.validator.UserCreateValidator

@RestController
@RequestMapping( value = "v1/users" )
public class UserController extends BaseController {

    @Autowired
    def ConversionService conversionService

    @Autowired
    def UserOperation userOperation

    @InitBinder
    public static void initBinder( WebDataBinder binder ) {
        binder.addValidators( new UserCreateValidator() )
    }

    @PreAuthorize( value = "hasRole('admin')" )
    @RequestMapping( method = RequestMethod.GET )
    def index( Pageable pageable, Authentication authentication ) {

        logger.info( "entity index, size:{}, number:{}, offset:{}, operator:{}",
                pageable.pageSize, pageable.pageNumber, pageable.offset, authentication.name)

        def userPages = userOperation.index( pageable )

        def userObjects = conversionService.convert(
                userPages.content,
                TypeDescriptor.collection( List.class, TypeDescriptor.valueOf( User.class ) ),
                TypeDescriptor.array( TypeDescriptor.valueOf( UserObject.class ) )
        )

        return toPageObjects( userObjects, userPages )
    }

    @PreAuthorize( value = "hasRole('admin')" )
    @RequestMapping( value = "{uid}", method = RequestMethod.GET )
    def show( @PathVariable( "uid" ) final String uid, Authentication authentication ) {

        logger.info( "user show, user:{}, operator:{}", uid, authentication.name)

        def user = userOperation.show( uid )

        conversionService.convert(
                user, UserObject.class
        )
    }

    @PreAuthorize( value = "hasRole('admin')" )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    @RequestMapping( value = "{uid}", method = RequestMethod.DELETE )
    def destroy( @PathVariable( "uid" ) final String uid, Authentication authentication ) {

        logger.info( "user delete, entity:{}, operator:{}", uid, authentication.name)

        userOperation.destroy( uid )
    }

    @PreAuthorize( value = "hasRole('admin')" )
    @ResponseStatus( HttpStatus.CREATED )
    @RequestMapping( method = RequestMethod.POST )
    def create(@Validated @RequestBody final UserObject userObject, BindingResult bindingResult, Authentication authentication ) {

        logger.info( "user create, userType:{}, UserDescription:{}, operator:{}",
                userObject.type, userObject.description, authentication.name )

        if( bindingResult.hasErrors() ) {
            throw new BindException( bindingResult )
        }

        def user = userOperation.create( userObject )

        conversionService.convert(
                user, UserObject.class
        )
    }

    @PreAuthorize( value = "hasRole('admin')" )
    @RequestMapping( value = "{uid}", method = RequestMethod.PUT )
    def update( @PathVariable( "uid" ) final String uid, @RequestBody final UserObject userObject, Authentication authentication ) {

        logger.info( "user update, user:{}, userType:{}, UserDescription:{}, operator:{}",
                uid, userObject.type, userObject.description, authentication.name )

        def user = userOperation.update( uid, userObject )

        conversionService.convert(
                user, UserObject.class
        )
    }

    @PreAuthorize( value = "hasRole('admin') or ( hasRole('common') and #uid == authentication.name )" )
    @RequestMapping( value = "{uid}/entities", method = RequestMethod.GET )
    def showAllEntities( @PathVariable( "uid" ) final String uid,
                         @RequestParam( value = "authorized", required = false ) final Boolean authorized,
                         Pageable pageable, Authentication authentication ) {

        logger.info( "user entities show, authorized:{}, size:{}, number:{}, offset:{}, operator:{}",
                authorized, pageable.pageSize, pageable.pageNumber, pageable.offset, authentication.name)

        def objectPages = userOperation.showEntities( uid, authorized, pageable )
        def entityObjects = conversionService.convert(
                objectPages.content,
                TypeDescriptor.collection( List.class, TypeDescriptor.valueOf( [].class ) ),
                TypeDescriptor.array( TypeDescriptor.valueOf( EntityAuthorizedObject.class ) )
        )

        return toPageObjects( entityObjects, objectPages )
    }

}