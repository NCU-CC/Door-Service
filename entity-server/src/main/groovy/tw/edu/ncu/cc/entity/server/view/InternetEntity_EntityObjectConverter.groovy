package tw.edu.ncu.cc.entity.server.view

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.entity.data.v1.EntityObject
import tw.edu.ncu.cc.entity.server.model.InternetEntity

@Component
class InternetEntity_EntityObjectConverter implements Converter< InternetEntity, EntityObject > {

    @Override
    EntityObject convert(InternetEntity source ) {
        EntityObject entityObject = new EntityObject();
        entityObject.id = source.uuid
        entityObject.name = source.name
        entityObject.ip = source.ip
        entityObject.creatorId = source.creator.uid
        entityObject.createdAt = source.createdAt
        entityObject.updatedAt = source.updatedAt
        entityObject
    }

}
