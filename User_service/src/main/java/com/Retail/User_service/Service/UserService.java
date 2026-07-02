package com.Retail.User_service.Service;

import com.Retail.User_service.Entity.User;
import com.Retail.User_service.Entity.UserRole;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUser(Long id);

   Boolean isAuthenticated(Long id);

    List<User> getAllUsers();

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    List<User> getUsersByRole(UserRole role);

    List<User> getUsersByCity(String city);

    List<User> getUsersByState(String state);
}
