package org.handmk.websocketstudy.data.repository;

import org.handmk.websocketstudy.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
