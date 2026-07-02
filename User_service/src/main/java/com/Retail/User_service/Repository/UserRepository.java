package com.Retail.User_service.Repository;


import com.Retail.User_service.Entity.User;
import com.Retail.User_service.Entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    List<User> findByRole(UserRole role);

    List<User> findByCity(String city);

    List<User> findByState(String state);
}
