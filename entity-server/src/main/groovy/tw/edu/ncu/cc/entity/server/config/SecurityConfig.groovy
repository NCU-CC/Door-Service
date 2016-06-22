package tw.edu.ncu.cc.entity.server.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import tw.edu.ncu.cc.entity.server.middleware.UserTypeFilter
import tw.edu.ncu.cc.oauth.resource.filter.AccessTokenDecisionFilter

@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class SecurityConfig {

    @Order( 1 )
    @Configuration
    public static class OauthGuard1 extends WebSecurityConfigurerAdapter {

        @Autowired
        def AccessTokenDecisionFilter accessTokenDecisionFilter

        @Autowired
        def UserTypeFilter userTypeFilter

        @Override
        protected void configure( HttpSecurity http ) throws Exception {
            http.requestMatchers()
                    .antMatchers( "/v*/entities/**" )
                    .antMatchers( "/v*/authorizations/**" )
                    .antMatchers( "/v*/users/**" )
                    .antMatchers( "/v*/whoami/**" )
                    .and()
                    .addFilterBefore( accessTokenDecisionFilter, UsernamePasswordAuthenticationFilter )
                    .addFilterAfter( userTypeFilter,  UsernamePasswordAuthenticationFilter)
                    .csrf().disable()
        }
    }

}