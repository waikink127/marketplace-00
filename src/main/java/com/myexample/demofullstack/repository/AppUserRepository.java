package com.myexample.demofullstack.repository;

import com.myexample.demofullstack.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findFirstByUsernameAndEnabled(String username, boolean enabled);



}
