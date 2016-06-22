package tw.edu.ncu.cc.entity.server.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import tw.edu.ncu.cc.entity.server.model.InternetEntity
import tw.edu.ncu.cc.entity.server.model.InternetEntityRepository
import tw.edu.ncu.cc.entity.server.model.User

@Service
class EntityServiceImpl implements EntityService {

    @Autowired
    InternetEntityRepository entityRepository

    @Override
    InternetEntity create( InternetEntity entity ) {
        entity.uuid = UUID.randomUUID().toString()
        entityRepository.save( entity )
    }

    @Override
    InternetEntity update( InternetEntity entity ) {
        entityRepository.save( entity )
    }

    @Override
    InternetEntity findByUUID( String uuid ) {
        entityRepository.findByUuid( uuid )
    }

    @Override
    InternetEntity findByIp(String ip) {
        entityRepository.findByIp( ip )
    }

    @Override
    Page<InternetEntity> findAll( Pageable pageable ) {
        entityRepository.findAll( pageable )
    }

    @Override
    Page<InternetEntity> findByCreator( User user, Pageable pageable ) {
        entityRepository.findByCreator( user, pageable )
    }

    @Override
    Page<InternetEntity> findAuthorizedByUser( User user, Pageable pageable ) {
        entityRepository.findAuthorizedByUser( user, pageable )
    }

    @Override
    void delete( InternetEntity entity ) {
        entityRepository.delete( entity.id )
    }
}
