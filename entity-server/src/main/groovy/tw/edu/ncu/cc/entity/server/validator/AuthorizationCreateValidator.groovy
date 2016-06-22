package tw.edu.ncu.cc.entity.server.validator

import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator
import tw.edu.ncu.cc.entity.data.v1.AuthorizationObject

public class AuthorizationCreateValidator implements Validator {

    @Override
    public boolean supports( Class< ? > clazz ) {
        return AuthorizationObject.class.equals( clazz )
    }

    @Override
    public void validate( Object target, Errors errors ) {
        ValidationUtils.rejectIfEmpty( errors, "authorizeeId", "authorizeeId.necessary", "authorizeeId is necessary" )
        ValidationUtils.rejectIfEmpty( errors, "entityId", "entityId.necessary", "entityId is necessary" )
    }

}
