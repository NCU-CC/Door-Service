package tw.edu.ncu.cc.entity.server.view

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

import tw.edu.ncu.cc.entity.data.v1.UserObject
import tw.edu.ncu.cc.entity.server.model.User

@Component
class User_UserObjectConverter implements Converter< User, UserObject > {

    @Override
    UserObject convert(User source ) {
        UserObject userObject = new UserObject();
        userObject.id = source.uid
        userObject.type = source.type
        userObject.description = source.description
        userObject.createdAt = source.createdAt
        userObject.updatedAt = source.updatedAt
        userObject
    }

}
