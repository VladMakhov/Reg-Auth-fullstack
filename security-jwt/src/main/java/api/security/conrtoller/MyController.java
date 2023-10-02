package api.security.conrtoller;

import api.security.model.User;
import api.security.model.UserDetailsImpl;
import api.security.model.dto.ExchangeRequest;
import api.security.repo.UserRepository;
import api.security.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@Log4j2
public class MyController {

    private final UserService userService;
    private final UserRepository userRepository;

    public MyController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/public")
    public ResponseEntity<String> publicPage() {
        return new ResponseEntity<>("You are in public page", HttpStatus.OK);
    }

    @GetMapping("/self")
    public ResponseEntity<?> privatePage() {
        return new ResponseEntity<>(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), HttpStatus.OK);
    }

    @PatchMapping("/exchange")
    public ResponseEntity<?> exchange(@RequestBody ExchangeRequest exchangeRequest) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User userFrom = userRepository.findUserByUsername(userDetails.getUsername())
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        User userTo = userRepository.findById(Integer.parseInt(exchangeRequest.getUserId()))
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        boolean b = userService.exchange(userFrom, userTo, Integer.parseInt(exchangeRequest.getAmount()));
        if (b) return new ResponseEntity<>("OK", HttpStatus.OK);
        else return new ResponseEntity<>("WRONG", HttpStatus.BAD_REQUEST);
    }

}
