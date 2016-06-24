package tw.edu.ncu.cc.entity.server.web

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import tw.edu.ncu.cc.entity.data.v1.PageMetaObject
import tw.edu.ncu.cc.entity.data.v1.PageObject

public class BaseController {

    protected Logger logger = LoggerFactory.getLogger( this.getClass() )

    protected static <T> PageObject<T> toPageObjects( Object content, Page<T> page ) {
        new PageObject< T >(
                content: content,
                pageMetadata: new PageMetaObject(
                        size: page.size,
                        totalElements: page.totalElements,
                        totalPages: page.totalPages,
                        number: page.number
                )
        )
    }

}