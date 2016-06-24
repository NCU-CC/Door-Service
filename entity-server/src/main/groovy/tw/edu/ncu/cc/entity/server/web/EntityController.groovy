package tw.edu.ncu.cc.entity.server.web

import org.slf4j.Logger
import org.slf4j.LoggerFactory
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
import tw.edu.ncu.cc.entity.data.v1.AuthorizationTokenObject
import tw.edu.ncu.cc.entity.data.v1.EntityObject
import tw.edu.ncu.cc.entity.data.v1.UserObject
import tw.edu.ncu.cc.entity.server.model.InternetEntity
import tw.edu.ncu.cc.entity.server.model.User
import tw.edu.ncu.cc.entity.server.operation.EntityOperation
import tw.edu.ncu.cc.entity.server.validator.EntityCreateValidator

@RestController
@RequestMapping( value = "v1/entities" )
public class EntityController extends BaseController {

    @Autowired
    def ConversionService conversionService

    @Autowired
    def EntityOperation entityOperation

    @InitBinder
    public static void initBinder( WebDataBinder binder ) {
        binder.addValidators( new EntityCreateValidator() )
    }

    @PreAuthorize( value = "hasRole('admin')" )
    @RequestMapping( method = RequestMethod.GET )
    def index( Pageable pageable, Authentication authentication ) {

        logger.info( "entity index, size:{}, number:{}, offset:{}, operator:{}",
                pageable.pageSize, pageable.pageNumber, pageable.offset, authentication.name)

        def entityPages = entityOperation.index( pageable )

        def entityObjects = conversionService.convert(
                entityPages.content,
                TypeDescriptor.collection( List.class, TypeDescriptor.valueOf( InternetEntity.class ) ),
                TypeDescriptor.array( TypeDescriptor.valueOf( EntityObject.class ) )
        )

        return toPageObjects( entityObjects, entityPages )
    }

    @PreAuthorize( value = "hasRole('admin')" )
    @RequestMapping( value = "{uuid}", method = RequestMethod.GET )
    def show( @PathVariable( "uuid" ) final String uuid, Authentication authentication ) {

        logger.info( "entity show, entity:{}, operator:{}", uuid, authentication.name)

        def entity = entityOperation.show( uuid )

        conversionService.convert(
                entity, EntityObject.class
        )
    }

    @PreAuthorize( value = "hasRole('admin')" )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    @RequestMapping( value = "{uuid}", method = RequestMethod.DELETE )
    def destroy( @PathVariable( "uuid" ) final String uuid, Authentication authentication ) {

        logger.info( "entity delete, entity:{}, operator:{}", uuid, authentication.name)

        entityOperation.destroy( uuid )
    }

    @PreAuthorize( value = "hasRole('admin')" )
    @ResponseStatus( HttpStatus.CREATED )
    @RequestMapping( method = RequestMethod.POST )
    def create(@Validated @RequestBody final EntityObject entityObject, BindingResult bindingResult, Authentication authentication ) {

        logger.info( "entity create, entityName:{}, entityIp:{}, operator:{}",
              entityObject.name, entityObject.ip, authentication.name )

        if( bindingResult.hasErrors() ) {
            throw new BindException( bindingResult )
        }

        def entity = entityOperation.create( entityObject, authentication.name )

        conversionService.convert(
                entity, EntityObject.class
        )
    }

    @PreAuthorize( value = "hasRole('admin')" )
    @RequestMapping( value = "{uuid}", method = RequestMethod.PUT )
    def update( @PathVariable( "uuid" ) final String uuid, @RequestBody final EntityObject entityObject, Authentication authentication ) {

        logger.info( "entity update, entity:{}, entityName:{}, entityIp:{}, operator:{}",
                uuid, entityObject.name, entityObject.ip, authentication.name )

        def entity = entityOperation.update( uuid, entityObject )

        conversionService.convert(
                entity, EntityObject.class
        )
    }

    @PreAuthorize( value = "hasRole('admin')" )
    @RequestMapping( value = "{uuid}/authorizees", method = RequestMethod.GET )
    def showAuthorizees( @PathVariable( "uuid" ) final String uuid, Pageable pageable, Authentication authentication ) {

        logger.info( "entity authorizees show, entity:{}, size:{}, number:{}, offset:{}, operator:{}",
                uuid, pageable.pageSize, pageable.pageNumber, pageable.offset, authentication.name)


        def userPages = entityOperation.showAuthorities( uuid, pageable )

        def userObjects = conversionService.convert(
                userPages.content,
                TypeDescriptor.collection( List.class, TypeDescriptor.valueOf( User.class ) ),
                TypeDescriptor.array( TypeDescriptor.valueOf( UserObject.class ) )
        )

        return toPageObjects( userObjects, userPages )
    }

    @PreAuthorize( value = "hasRole('admin') or hasRole('common')" )
    @RequestMapping( value = "{uuid}/authorization_tokens", method = RequestMethod.POST )
    def createToken( @PathVariable( "uuid" ) final String uuid, Authentication authentication ) {

        logger.info( "entity token create, entity:{}, operator:{}", uuid, authentication.name )

        def token = entityOperation.createToken( uuid, authentication.name )

        conversionService.convert(
                token, AuthorizationTokenObject.class
        )
    }

}