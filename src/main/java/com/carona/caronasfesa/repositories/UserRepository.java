package com.carona.caronasfesa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carona.caronasfesa.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {}
