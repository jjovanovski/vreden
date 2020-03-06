package com.jj.vreden.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JWTUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_HEADER_CONTENT_PREFIX = "Bearer";

    private static final String JWT_SECRET = "vreden_jwt_secret";

    public static String generateJwtToken(String subject, Map<String, Object> claims, int durationInSeconds) {
        Date now = new Date();
        Date expirationDate = GetExpirationDateAfterSeconds(now, durationInSeconds);

        return Jwts.builder()
                .setSubject(subject)
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    public static Jws<Claims> tryParseJwtToken(String token) throws Exception {
        Jws<Claims> jwtClaims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
        Claims claims = jwtClaims.getBody();

        Date now = new Date();
        if(claims != null && now.after(claims.getExpiration())) {
            throw new Exception("Invalid token");
        }

        return jwtClaims;
    }

    private static Date GetExpirationDateAfterSeconds(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);

        return calendar.getTime();
    }

}
