package tw.edu.ncu.cc.entity.server.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tw.edu.ncu.cc.entity.server.middleware.UserTypeFilter

@Configuration
class BeanConfig {

    @Autowired
    UserTypeFilter userTypeFilter

    @Bean
    FilterRegistrationBean userTypeFilterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean( userTypeFilter )
        registrationBean.setEnabled( false )
        registrationBean
    }

}
