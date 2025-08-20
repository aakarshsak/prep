package com.zoro.preparation.prep_app;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping
    public String addUser(@Valid @RequestBody User user) {
        userRepository.save(User.builder().name(user.getName()).email(user.getEmail()).role(user.getRole()).build());
        return "Success";
    }

    @GetMapping("/app-info")
    public ResponseEntity<AppProperties> getAppInfo() {
        return ResponseEntity.ok(appProperties);
    }
}
