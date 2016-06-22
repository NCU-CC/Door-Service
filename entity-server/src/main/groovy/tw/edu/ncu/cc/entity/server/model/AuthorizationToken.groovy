package tw.edu.ncu.cc.entity.server.model

import javax.persistence.*

@Entity
class AuthorizationToken implements Serializable {

    @Id
    @GeneratedValue
    def Integer id

    @Version
    def Integer version = 0

    @Column( nullable = false )
    def String token

    @JoinColumn
    @ManyToOne( optional = false )
    def InternetEntity entity

    @JoinColumn
    @ManyToOne( optional = false )
    def User creator

    @Temporal( value = TemporalType.TIMESTAMP )
    def Date expiredAt

    @Temporal( value = TemporalType.TIMESTAMP )
    def Date createdAt

    @PrePersist
    protected void onCreate() {
        createdAt = new Date()
    }

}
