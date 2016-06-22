package tw.edu.ncu.cc.entity.server.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.context.embedded.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tw.edu.ncu.cc.entity.server.middleware.UserTypeFilter

@Configuration
class BeanConfig {

    @Bean
    @ConditionalOnBean( UserTypeFilter )
    FilterRegistrationBean apiTokenDecisionFilterRegistration( UserTypeFilter userTypeFilter ) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean( userTypeFilter )
        registrationBean.setEnabled( false )
        registrationBean
    }

}
