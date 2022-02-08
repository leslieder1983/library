package com.manage.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Date;
//读取配置信息
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "blog.jwt")
public class JwtUtils {
    private String secret;
    private long expire;
    private String header;



    /**
     * 生成jwt token
     */
    public  String generateToken(long userId) {
    Date date=new Date();
    Date expireDate=new Date(date.getTime()+expire*1000);
    return Jwts.builder()
            .setHeaderParam("typ","JWT")
            .setSubject(userId+" ")
            .setIssuedAt(date)
            .setExpiration(expireDate)
            .signWith(SignatureAlgorithm.HS512,secret)
            .compact();
    }

    // 获取jwt的信息
    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            //Todo log4j整合失败
//            log.debug("validate is token error",e);
            return null;
        }
    }

    /**
     * token是否过期
     * @return  true：过期
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
