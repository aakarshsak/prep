package com.zoro.preparation.prep_app;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User addUser(User user);
}
