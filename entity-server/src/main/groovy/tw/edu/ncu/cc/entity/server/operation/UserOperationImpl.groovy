package tw.edu.ncu.cc.entity.server.operation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpServerErrorException
import tw.edu.ncu.cc.entity.data.v1.UserObject
import tw.edu.ncu.cc.entity.server.model.InternetEntity
import tw.edu.ncu.cc.entity.server.model.User
import tw.edu.ncu.cc.entity.server.service.EntityService
import tw.edu.ncu.cc.entity.server.service.UserService

@Component
@Transactional
class UserOperationImpl implements UserOperation {

    @Autowired
    def EntityService entityService

    @Autowired
    def UserService userService

    @Override
    Page<User> index(Pageable pageable) {
        userService.findAll(pageable)
    }

    @Override
    Page<InternetEntity> showAuthorizedEntities(String uid, Pageable pageable) {
        def user = show( uid )
        return entityService.findAuthorizedByUser( user, pageable )
    }

    @Override
    User show(String uid) {
        def user = userService.findByUID(uid)
        if (user == null) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, "required resource is not found")
        }
        return user
    }

    @Override
    User create(UserObject userObject) {
        return userService.create(new User(
                uid: userObject.id,
                description: userObject.description,
                type: toType(userObject.type)
        ))
    }

    private static User.Type toType(String type) {
        type.equals("admin") ? User.Type.admin : User.Type.common
    }

    @Override
    User update(String uid, UserObject userObject) {
        def user = show( uid )
        user.description = userObject.description
        user.type = toType(userObject.type)
        return userService.update(user)
    }

    @Override
    void destroy(String uid) {
        def user = show( uid )
        userService.delete(user)
    }
}
