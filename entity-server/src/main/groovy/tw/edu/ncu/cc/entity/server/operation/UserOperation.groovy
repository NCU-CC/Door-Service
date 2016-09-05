package tw.edu.ncu.cc.entity.server.operation

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tw.edu.ncu.cc.entity.data.v1.EntityObject
import tw.edu.ncu.cc.entity.data.v1.UserObject
import tw.edu.ncu.cc.entity.server.model.InternetEntity
import tw.edu.ncu.cc.entity.server.model.User

interface UserOperation {

    Page<User> index( Pageable pageable )
    Page<Object[]> showEntities( String uid, Boolean isAuthorized, Pageable pageable )
    User show( String uid )
    User create( UserObject userObject )
    User update( String uid, UserObject userObject )
    void destroy( String uid )
}
