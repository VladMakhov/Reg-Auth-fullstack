package api.security.conrtoller;

import api.security.filter.JwtFilter;
import api.security.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MyController {

    private final JwtUtil jwtUtil;
    private final JwtFilter jwtFilter;

    public MyController(JwtUtil jwtUtil, JwtFilter jwtFilter) {
        this.jwtUtil = jwtUtil;
        this.jwtFilter = jwtFilter;
    }

    @GetMapping("/public")
    public ResponseEntity<String> publicPage() {
        return new ResponseEntity<>("You are in public page", HttpStatus.OK);
    }

    @GetMapping("/self")
    public ResponseEntity<String> privatePage(HttpServletRequest request) {
        String token = jwtFilter.parseJwt(request);
        return new ResponseEntity<>(jwtUtil.getUsernameFromToken(token), HttpStatus.OK);
    }

}
