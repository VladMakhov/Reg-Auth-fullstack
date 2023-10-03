package api;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.Key;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class SecurityControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void signup() {

        try {
            mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/auth/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                        {
                                            "username": "username",
                                            "password": "password"
                                        }
                                    """))
                    .andDo(print())
                    .andExpect(MockMvcResultMatchers.status().isOk());

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Test
    void signin() {
        Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = new TestingAuthenticationToken("username", "password");
        
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        SecurityContext context2 = SecurityContextHolder.getContext();
        Authentication authentication2 = context2.getAuthentication();

        String username = authentication2.getName();
        Object principal = authentication2.getPrincipal();

        log.info("username = " + username);
        log.info("principal = " + principal);

        String token = Jwts
                .builder()
                .setSubject("username")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 1000))
                .signWith(SECRET_KEY)
                .compact();

        log.info("token = " + token);

        Assertions.assertEquals(username, "username");
        Assertions.assertEquals(principal, "username");
        Assertions.assertNotNull(token);

        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            log.info("jwt token is valid");
        }  catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
    }
    
}