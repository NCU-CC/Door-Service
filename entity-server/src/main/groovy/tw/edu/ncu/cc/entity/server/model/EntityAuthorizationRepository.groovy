package tw.edu.ncu.cc.entity.server.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

public interface EntityAuthorizationRepository extends JpaRepository< EntityAuthorization, Integer >, JpaSpecificationExecutor< EntityAuthorization > {

    EntityAuthorization findByAuthorizeeAndEntity( User authorizee, InternetEntity entity )

}
