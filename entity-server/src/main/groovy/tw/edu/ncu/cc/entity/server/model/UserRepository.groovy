package tw.edu.ncu.cc.entity.server.model

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

public interface UserRepository extends JpaRepository< User, Integer >, JpaSpecificationExecutor< User > {

    User findByUid( String uid )

    @Query("SELECT u FROM User u JOIN u.authorizedEntities ue WHERE ue = (:entity)")
    Page<User> findAuthorizeesByEntity( @Param("entity") InternetEntity entity, Pageable pageable )

}
