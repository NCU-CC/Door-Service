package tw.edu.ncu.cc.entity.server.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tw.edu.ncu.cc.entity.server.model.InternetEntity
import tw.edu.ncu.cc.entity.server.model.User


interface EntityService {
    InternetEntity create( InternetEntity entity )
    InternetEntity update( InternetEntity entity )
    InternetEntity findByUUID( String uuid )
    InternetEntity findByIp( String ip )
    Page< InternetEntity > findAll( Pageable pageable )
    Page< InternetEntity > findAuthorizedByUser( User user, Pageable pageable )
    Page< InternetEntity > findByCreator( User user, Pageable pageable )
    void delete( InternetEntity entity )
}