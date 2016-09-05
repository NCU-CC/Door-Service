package tw.edu.ncu.cc.entity.server.view

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.entity.data.v1.EntityAuthorizedObject
import tw.edu.ncu.cc.entity.data.v1.EntityObject
import tw.edu.ncu.cc.entity.server.model.InternetEntity

@Component
class ObjectArray_EntityAuthorizedObjectConverter implements Converter< Object[], EntityAuthorizedObject > {

    @Override
    EntityAuthorizedObject convert(Object[] source ) {
        InternetEntity entity = source[0] as InternetEntity
        EntityAuthorizedObject entityObject = new EntityAuthorizedObject();
        entityObject.id = entity.uuid
        entityObject.name = entity.name
        entityObject.ip = entity.ip
        entityObject.isAuthorized = source[1] as boolean
        entityObject.creatorId = entity.creator.uid
        entityObject.createdAt = entity.createdAt
        entityObject.updatedAt = entity.updatedAt
        entityObject
    }

}
