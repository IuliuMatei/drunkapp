package com.DrinkApp.Fun.Repository;

import com.DrinkApp.Fun.Entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepo extends JpaRepository<PhotoEntity, Long> {
}
