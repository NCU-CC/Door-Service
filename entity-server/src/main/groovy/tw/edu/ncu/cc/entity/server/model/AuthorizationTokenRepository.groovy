package tw.edu.ncu.cc.entity.server.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

public interface AuthorizationTokenRepository extends JpaRepository< AuthorizationToken, Integer >, JpaSpecificationExecutor< AuthorizationToken > {

    @Query("SELECT t FROM AuthorizationToken t WHERE t.token = (:token) AND t.expiredAt > CURRENT_TIMESTAMP AND t.entity.ip = (:ip)")
    List<AuthorizationToken> findUnexpiredByTokenAndEntityIp( @Param("token") String token, @Param("ip") String entityIp )

}
