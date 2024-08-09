package com.individual.preonboardingbackend.user.repository;

import com.individual.preonboardingbackend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
