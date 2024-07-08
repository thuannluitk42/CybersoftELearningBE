package com.thuannluit.quizzes.repository;

import com.thuannluit.quizzes.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r from Role r where r.role_name like :name")
    Role findByRole_name(String name);
}
