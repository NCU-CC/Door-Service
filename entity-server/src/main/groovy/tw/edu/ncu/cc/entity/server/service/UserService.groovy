package tw.edu.ncu.cc.entity.server.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tw.edu.ncu.cc.entity.server.model.InternetEntity
import tw.edu.ncu.cc.entity.server.model.User


interface UserService {
    User create( User user )
    User update( User user )
    User findByUID( String uid )
    Page< User > findAll( Pageable pageable )
    Page< User > findByAuthorizedEntity( InternetEntity entity, Pageable pageable )
    void delete( User user )
}