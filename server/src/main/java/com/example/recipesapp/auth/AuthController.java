package com.example.recipesapp.auth;

import com.example.recipesapp.dto.SignInDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;
    private final IdTokenVerifier idTokenVerifier;

    @Autowired
    public AuthController(UserRepository userRepository,
                          IdTokenVerifier idTokenVerifier) {
        this.userRepository = userRepository;
        this.idTokenVerifier = idTokenVerifier;
    }

    @PostMapping("/sign-in")
    public ResponseEntity signIn(@RequestBody SignInDTO dto) {
        try {
            Payload payload = this.idTokenVerifier.verifyToken(dto.getIdToken());
            logger.info(payload.toString());

            String googleUserId = payload.getSubject();
            String googleUserEmail = payload.getEmail();
            String googleUserName = (String) payload.get("name");
            String googleUserAvatar = (String) payload.get("picture");
            User user = userRepository.findOne(googleUserId);
            if (user == null) {
                User newUser = new User();
                newUser.setId(googleUserId);
                newUser.setEmail(googleUserEmail);
                newUser.setName(googleUserName);
                newUser.setAvatarUrl(googleUserAvatar);
                userRepository.save(newUser);
            } else {
                if (!user.getName().equals(googleUserName)) {
                    user.setName(googleUserName);
                }
                if (!user.getEmail().equals(googleUserEmail)) {
                    user.setName(googleUserEmail);
                }
                userRepository.save(user);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidTokenException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
