package tw.edu.ncu.cc.entity.server.model

import javax.persistence.*

@Entity
class EntityAuthorization implements Serializable {

    @Id
    @GeneratedValue
    def Integer id

    @Version
    def Integer version = 0

    @JoinColumn
    @ManyToOne( optional = false )
    def User authorizee

    @JoinColumn
    @ManyToOne( optional = false )
    def User authorizer

    @JoinColumn
    @ManyToOne( optional = false )
    def InternetEntity entity

    @Temporal( value = TemporalType.TIMESTAMP )
    def Date createdAt

    @PrePersist
    protected void onCreate() {
        createdAt = new Date()
    }

}
