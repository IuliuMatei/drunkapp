package com.DrinkApp.Fun.Repository;

import com.DrinkApp.Fun.Entity.FriendshipEntity;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Utils.Enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepo extends JpaRepository<FriendshipEntity, Long> {

    Optional<FriendshipEntity> findByRequesterAndReceiver(UserEntity requester, UserEntity receiver);

    Optional<FriendshipEntity> findByRequesterAndReceiverAndStatus(UserEntity requester, UserEntity receiver, Status status);

    Optional<FriendshipEntity> findByReceiverAndRequesterAndStatus(UserEntity receiver, UserEntity requester, Status status);

}
