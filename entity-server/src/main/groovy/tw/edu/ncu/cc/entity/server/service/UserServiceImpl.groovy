package tw.edu.ncu.cc.entity.server.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import tw.edu.ncu.cc.entity.server.model.InternetEntity
import tw.edu.ncu.cc.entity.server.model.User
import tw.edu.ncu.cc.entity.server.model.UserRepository

@Service
class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository

    @Override
    User create( User user ) {
        userRepository.save( user )
    }

    @Override
    User update( User user ) {
        userRepository.save( user )
    }

    @Override
    User findByUID( String uid ) {
        userRepository.findByUid( uid )
    }

    @Override
    Page< User > findAll( Pageable pageable ) {
        userRepository.findAll( pageable )
    }

    @Override
    Page<User> findByAuthorizedEntity( InternetEntity entity, Pageable pageable ) {
        userRepository.findAuthorizeesByEntity( entity, pageable )
    }

    @Override
    void delete( User user ) {
        userRepository.delete( user.id )
    }
}
