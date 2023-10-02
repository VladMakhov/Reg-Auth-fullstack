package api.security.conrtoller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MyController {

    @GetMapping("/public")
    public ResponseEntity<String> publicPage() {
        return new ResponseEntity<>("You are in public page", HttpStatus.OK);
    }

    @GetMapping("/self")
    public ResponseEntity<?> privatePage() {
        return new ResponseEntity<>(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), HttpStatus.OK);
    }

}
