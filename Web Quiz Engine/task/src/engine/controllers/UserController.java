package engine.controllers;

import engine.enteties.User;
import engine.repo.UserRepo;
import engine.security.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    UserRepo userRepo;
    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/api/register")
    public User createUser(@Valid @RequestBody User user) {

        User newUser = new User(user.getId(), user.getEmail(), user.getPassword());

        if (userRepo.existsUserByEmail(newUser.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        newUser.setPassword(encoder.encode(newUser.getPassword()));

        userRepo.save(newUser);

        return newUser;

    }
}
