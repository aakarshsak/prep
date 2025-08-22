package com.zoro.preparation.prep_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        User savedUser = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        return userRepository.save(savedUser);
    }
}
