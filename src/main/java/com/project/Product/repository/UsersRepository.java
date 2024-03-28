package com.project.Product.repository;

import com.project.Product.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users,String> {

    Users findByEmail(String email);
}
