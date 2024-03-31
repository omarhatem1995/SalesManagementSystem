package com.quiz.sales_management_system.repo;

import com.quiz.sales_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}