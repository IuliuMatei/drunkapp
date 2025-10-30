package com.DrinkApp.Fun.Repository;

import com.DrinkApp.Fun.Entity.FriendshipEntity;
import com.DrinkApp.Fun.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendshipRepo extends JpaRepository<FriendshipEntity, Long> {

    Optional<FriendshipEntity> findByRequesterAndReceiver(UserEntity requester, UserEntity receiver);

}
