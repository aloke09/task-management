package com.bridgelabz.UserManagement1.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService
{
    private String secretKey="";

    public JWTService()
    {
        try
        {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey= Base64.getEncoder().encodeToString(sk.getEncoded());
            System.out.println("inside jwt service constructor key generated through HMACSHA256 and encoded");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }
    public String generateToken(String email)
    {
        Map<String,Object> claim =new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claim)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60*30))
                .and()
                .signWith(getKey())
                .compact();
    }
    private SecretKey getKey()
    {

        byte[] decode = Decoders.BASE64.decode(secretKey);
        System.out.println("inside jwt service getkey method");
        return Keys.hmacShaKeyFor(decode);

    }
    public String extractUserName(String token) // This method extracts the username from the JWT token.
    {
        System.out.println("inside jwt service extract username method");
        return extractClaim(token, Claims::getSubject);//This line calls the extractClaim method with the token and a method reference Claims::getSubject. The getSubject() method of the Claims object retrieves the subject, which is the username in this case.
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) // This is a generic method that extracts a specific claim from the token. The Function<Claims, T> parameter claimResolver is a functional interface used to apply a method (like getSubject) to the Claims object.
    {
        final Claims claims = extractAllClaims(token);//extractAllClaims(token);: This line extracts all claims from the token by calling the extractAllClaims method.
        return claimResolver.apply(claims);//to the Claims object and returns the result. This allows for flexible extraction of different types of claims from the token.
    }

    private Claims extractAllClaims(String token) //This method extracts all claims from the JWT token.
    {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails)
    {
        System.out.println("inside jwt service validate token method");
        final String userName = extractUserName(token);
        System.out.println(userName);

        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
