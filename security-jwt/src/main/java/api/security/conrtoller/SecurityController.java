package api.security.conrtoller;

import api.security.model.dto.LoginRequest;
import api.security.model.dto.LoginResponse;
import api.security.model.dto.RegistrationRequest;
import api.security.service.SecurityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/auth")
public class SecurityController {

    private final SecurityService service;

    public SecurityController(SecurityService service) {
        this.service = service;
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signin(@RequestBody LoginRequest request) {
        return service.login(request);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegistrationRequest request) {
        return service.registration(request);
    }

}
