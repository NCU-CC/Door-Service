package tw.edu.ncu.cc.entity.server.validator

import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator
import tw.edu.ncu.cc.entity.data.v1.EntityObject
import tw.edu.ncu.cc.entity.data.v1.UserObject

public class EntityCreateValidator implements Validator {

    @Override
    public boolean supports( Class< ? > clazz ) {
        return EntityObject.class.equals( clazz )
    }

    @Override
    public void validate( Object target, Errors errors ) {
        ValidationUtils.rejectIfEmpty( errors, "name", "name.necessary", "name is necessary" )

    }

}
