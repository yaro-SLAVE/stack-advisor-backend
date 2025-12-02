package com.stack_advisor.stack_advisor_backend.repositories;

import com.stack_advisor.stack_advisor_backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

