package com.Retail.User_service.ServiceImpl;


import com.Retail.User_service.Entity.User;
import com.Retail.User_service.Entity.UserRole;
import com.Retail.User_service.Repository.UserRepository;
import com.Retail.User_service.Service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User createUser(User user) {
        return repository.save(user);
    }

    public Boolean isAuthenticated(Long id) {

        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getAuthenticated();
    }

    @Override
    public User getUser(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {

        User existing = getUser(id);

        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());
        existing.setEmail(user.getEmail());
        existing.setPhoneNumber(user.getPhoneNumber());
        existing.setRole(user.getRole());
        existing.setCity(user.getCity());
        existing.setState(user.getState());
        existing.setActive(user.getActive());

        return repository.save(existing);
    }

    @Override
    public void deleteUser(Long id) {

        repository.deleteById(id);
    }

    @Override
    public List<User> getUsersByRole(UserRole role) {

        return repository.findByRole(role);
    }

    @Override
    public List<User> getUsersByCity(String city) {

        return repository.findByCity(city);
    }

    @Override
    public List<User> getUsersByState(String state) {

        return repository.findByState(state);
    }
}
