package com.sell.repository;

import com.sell.model.Cart;
import com.sell.model.Role;
import com.sell.model.User;
import org.springframework.beans.PropertyValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    User findByEmailAndPassword(String em,String pass);

    List<User> findByRole(Role adminRole);

    Optional<User> findByUserId(long id);

}
