package tw.edu.ncu.cc.entity.server.middleware

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.entity.server.service.UserService

import javax.servlet.*

@Component
class UserTypeFilter implements Filter {

    @Autowired
    UserService userService

    @Override
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        def authentication = SecurityContextHolder.getContext().getAuthentication()
        def principal = authentication.getPrincipal()
        def credential = authentication.getCredentials()
        def authorities = authentication.getAuthorities()
        def user = userService.findByUID( authentication.name )
        if( user != null ) {
            def scope = new LinkedList<GrantedAuthority>()
            scope.addAll( authorities )
            scope.add( new SimpleGrantedAuthority( "ROLE_" + user.type ) )
            SecurityContextHolder.getContext().setAuthentication( new UsernamePasswordAuthenticationToken(
                    principal, credential, scope
            ))
        }
        chain.doFilter( request, response )
    }

    @Override
    void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    void destroy() {

    }

}
