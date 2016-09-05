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

    @Query( value = "SELECT e, CASE WHEN (ea = :user) THEN TRUE ELSE FALSE END as authorized FROM InternetEntity e JOIN e.authorizees ea WHERE ea = (:user)",
            countQuery = "SELECT COUNT(e) FROM InternetEntity e JOIN e.authorizees ea WHERE ea = (:user)" )
    Page<Object[]> findAuthorizedByUser( @Param("user") User user, Pageable pageable )

    @Query( value = "SELECT e, CASE WHEN (ea = :user) THEN TRUE ELSE FALSE END as authorized FROM InternetEntity e JOIN e.authorizees ea WHERE ea != (:user)",
            countQuery = "SELECT COUNT(e) FROM InternetEntity e JOIN e.authorizees ea WHERE ea != (:user)" )
    Page<Object[]> findUnauthorizedByUser( @Param("user") User user, Pageable pageable )

    @Query( value = "SELECT e, CASE WHEN (ea = :user) THEN TRUE ELSE FALSE END as authorized FROM InternetEntity e JOIN e.authorizees ea",
            countQuery = "SELECT COUNT(e) FROM InternetEntity e WHERE :user != NULL")
    Page<Object[]> findIsAuthorizedByUser( @Param("user") User user, Pageable pageable )

}
