package tw.edu.ncu.cc.entity.server.model

import javax.persistence.*

@Entity
class User implements Serializable {

    enum Type {
        common, admin
    }

    @Id
    @GeneratedValue
    def Integer id

    @Column( unique = true, nullable = false )
    def String uid

    @Column
    def String description

    @Enumerated( EnumType.ORDINAL )
    def Type type = Type.common

    @Version
    def Integer version = 0

    @Temporal( value = TemporalType.TIMESTAMP )
    def Date updatedAt

    @Temporal( value = TemporalType.TIMESTAMP )
    def Date createdAt

    @ManyToMany
    @JoinTable(
            name = "EntityAuthorization",
            joinColumns = @JoinColumn( name = "authorizee_id" ),
            inverseJoinColumns = @JoinColumn( name = "entity_id" )
    )
    def Set< InternetEntity > authorizedEntities

    @ManyToMany
    @JoinTable(
            name = "EntityAuthorization",
            joinColumns = @JoinColumn( name = "authorizer_id" ),
            inverseJoinColumns = @JoinColumn( name = "entity_id" )
    )
    def Set< InternetEntity > ownedEntities

    @PrePersist
    protected void onCreate() {
        createdAt = new Date()
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date()
    }

}
