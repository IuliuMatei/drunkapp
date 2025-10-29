package com.DrinkApp.Fun.Repository;

import com.DrinkApp.Fun.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    public Optional<UserEntity> findByUname(String username);

    public Optional<UserEntity> findByEmail(String email);

}
