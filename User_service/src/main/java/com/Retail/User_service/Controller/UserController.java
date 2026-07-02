package com.Retail.User_service.Controller;


import com.Retail.User_service.Entity.User;
import com.Retail.User_service.Entity.UserRole;
import com.Retail.User_service.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User create(@RequestBody User user) {

        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {

        return userService.getUser(id);
    }

    @GetMapping("/{id}/authenticated")
    public Boolean isAuthenticated(@PathVariable Long id) {
        return userService.isAuthenticated(id);
    }

    @GetMapping
    public List<User> getAll() {

        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public User update(
            @PathVariable Long id,
            @RequestBody User user) {

        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {

        userService.deleteUser(id);

        return "User deleted successfully.";
    }

    @GetMapping("/role/{role}")
    public List<User> byRole(
            @PathVariable UserRole role) {

        return userService.getUsersByRole(role);
    }

    @GetMapping("/city/{city}")
    public List<User> byCity(
            @PathVariable String city) {

        return userService.getUsersByCity(city);
    }

    @GetMapping("/state/{state}")
    public List<User> byState(
            @PathVariable String state) {

        return userService.getUsersByState(state);
    }
}
