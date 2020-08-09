package com.paymybuddy.PayMyBuddyWeb.services;

import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.SecurityDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.UserDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.spec.SecretKeySpec;
import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class SecurityService implements SecurityServiceInterface {
    /**
     * Logger log4j2
     */
    private static final Logger logger = LogManager.getLogger("SecurityService");

    /**
     * Security DAO
     */
    private SecurityDAOInterface securityDAO;

    /**
     * User DAO
     */
    private UserDAOInterface userDAO;

    /**
     * Password manager
     */
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String JWTKey;

    /**
     * Constructor
     * @param securityDAO
     */
    public SecurityService(SecurityDAOInterface securityDAO, BCryptPasswordEncoder passwordEncoder, UserDAOInterface userDAO) {
        this.securityDAO = securityDAO;
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
    }

    /**
     * @see SecurityDAOInterface {@link #registerUser(Integer, String)}
     */
    @Override
    public void registerUser(Integer userId, String password) {
        securityDAO.updatePassword(userId, passwordEncoder.encode(password));
    }

    /**
     * @see SecurityDAOInterface {@link #logUser(String, String)}
     */
    @Override
    public Map<String, String> logUser(String username, String password) {
        Map<String, String> loginInformations = null;
        String encodePassword = securityDAO.getUserPassword(username);
        if (encodePassword != null && !encodePassword.isEmpty() && passwordEncoder.matches(password, encodePassword)) {
            loginInformations = new HashMap<>();
            User user = userDAO.getUserByUsername(username);

            Map<String, Object> claims = new HashMap<>();
            claims.put("username", user.getEmail());
            claims.put("name", user.getFirstName() + " " + user.getLastName());
            loginInformations.put("token", createJWT(user.getId(), "Login", "PayMyBuddy", claims, 1000 * 60 * 60 * 5));
        }
        return loginInformations;
    }

    /**
     * @see SecurityDAOInterface {@link #createJWT(Integer, String, String, Map, long)}
     */
    public String createJWT(Integer id, String subject, String issuer, Map<String, Object> claims, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(JWTKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setId(id.toString())
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .setClaims(claims)
                .signWith(signatureAlgorithm, signingKey);

        if (ttlMillis >= 0) {
            builder.setExpiration(new Date(nowMillis + ttlMillis));
        }

        return builder.compact();
    }

    /**
     * @see SecurityDAOInterface {@link #parseJWT(String)}
     */
    public Claims parseJWT(String token) {
        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(JWTKey))
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            logger.error("Invalid token", token);
        }

        return claims;
    }

    /**
     * @see SecurityDAOInterface {@link #isLog(HttpSession)}
     */
    public Boolean isLog(HttpSession session) {
        String token = (String) session.getAttribute("token");
        return ((token != null) && (parseJWT(token) != null));
    }
}
