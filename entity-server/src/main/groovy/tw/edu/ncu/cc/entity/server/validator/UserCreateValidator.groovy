package tw.edu.ncu.cc.entity.server.validator

import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

import tw.edu.ncu.cc.entity.data.v1.UserObject

public class UserCreateValidator implements Validator {

    @Override
    public boolean supports( Class< ? > clazz ) {
        return UserObject.class.equals( clazz )
    }

    @Override
    public void validate( Object target, Errors errors ) {
        ValidationUtils.rejectIfEmpty( errors, "id", "id.necessary", "id is necessary" )
        ValidationUtils.rejectIfEmpty( errors, "type", "type.necessary", "type is necessary" )
    }

}
