package tw.edu.ncu.cc.entity.server.model

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

public interface InternetEntityRepository extends JpaRepository< InternetEntity, Integer >, JpaSpecificationExecutor< InternetEntity > {

    InternetEntity findByUuid( String uuid )

    InternetEntity findByIp( String ip )

    Page<InternetEntity> findByCreator( User user, Pageable pageable )

    @Query("SELECT e FROM InternetEntity e JOIN e.authorizees ea WHERE ea = (:user)")
    Page<InternetEntity> findAuthorizedByUser( @Param("user") User user, Pageable pageable )

}
