package com.example.demo.controller.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;

public class Tools {
    static String secret = "dfsfdsdfewewfadsfadsf1!@#$6374384123!!@##88";

    public static String createToken(String userId, String userName, String roleId, int start) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token =
                    JWT.create()
                            .withIssuer("auth0")
                            .withClaim("userId", userId)
                            .withClaim("userName", userName)
                            .withClaim("roleId", roleId)
                            .withClaim("start", start)
                            .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            // Invalid Signing configuration / Couldn't convert Claims.
        }
        return "";
    }

    public static DecodedJWT verifyToken(String token) {
        //  String token =
        // "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXUyJ9.eyJpc3MiOiJhdXRoMCJ9.AbIJTDMFc7yUa5MhvcP03nJPyCPzZtQcGEp-zWfOkEE";
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret); // use more secure key
            JWTVerifier verifier =
                    JWT.require(algorithm).withIssuer("auth0").build(); // Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (Exception exception) {
            return null;
            // Invalid signature/claims
        }
    }

    public static String getInIp(HttpServletRequest req) {
        String ip = req.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    public static String getBrowser(HttpServletRequest req) {
        return req.getHeader("User-Agent").substring(0, 99);
    }

    public static String getUserId(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim("userId").asString();
    }
}
