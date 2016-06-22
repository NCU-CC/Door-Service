package tw.edu.ncu.cc.entity.server.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tw.edu.ncu.cc.entity.server.model.AuthorizationToken
import tw.edu.ncu.cc.entity.server.model.AuthorizationTokenRepository

@Service
class AuthorizationTokenServiceImpl implements AuthorizationTokenService {

    @Autowired
    AuthorizationTokenRepository authorizationTokenRepository

    @Override
    AuthorizationToken create( AuthorizationToken authorizationToken ) {
        authorizationToken.token = generateRandom5DigitString()
        authorizationToken.expiredAt = generateExpiredDate()
        authorizationTokenRepository.save( authorizationToken )
    }

    private static Date generateExpiredDate() {
        Calendar c = Calendar.getInstance();
        c.setTime( new Date() );
        c.add(Calendar.MINUTE, 1);
        c.getTime();
    }

    @Override
    AuthorizationToken findUnexpiredByTokenAndEntityIp( String token, String ip ) {
        def tokens = authorizationTokenRepository.findUnexpiredByTokenAndEntityIp( token, ip )
        if( tokens.size() > 0 ){
            return tokens.get( 0 )
        }
        return null
    }

    private static String generateRandom5DigitString() {
        Random r = new Random( System.currentTimeMillis() )
        return String.format( "%05d", r.nextInt( 100000 ) )
    }

}
