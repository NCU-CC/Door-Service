package tw.edu.ncu.cc.entity.server.model

import javax.persistence.*

@Entity
class InternetEntity implements Serializable {

    @Id
    @GeneratedValue
    def Integer id

    @Column( nullable = false, unique = true )
    def String uuid

    @Column( nullable = false )
    def String name

    @Column( unique = true )
    def String ip

    @Version
    def Integer version = 0

    @ManyToMany
    @JoinTable(
            name = "EntityAuthorization",
            joinColumns = @JoinColumn( name = "entity_id" ),
            inverseJoinColumns = @JoinColumn( name = "authorizee_id" )
    )
    def Set< User > authorizees

    @JoinColumn
    @ManyToOne( optional = false )
    def User creator

    @Temporal( value = TemporalType.TIMESTAMP )
    def Date updatedAt

    @Temporal( value = TemporalType.TIMESTAMP )
    def Date createdAt

    @PrePersist
    protected void onCreate() {
        createdAt = new Date()
        updatedAt = createdAt
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date()
    }

}
