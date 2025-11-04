package com.DrinkApp.Fun.Repository;

import com.DrinkApp.Fun.Entity.PostEntity;
import com.DrinkApp.Fun.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<PostEntity, Long> {

    @Query("""
    SELECT p FROM PostEntity p
    WHERE p.user.id IN (
        SELECT CASE
            WHEN f.requester.id = :userId THEN f.receiver.id
            ELSE f.requester.id
        END
        FROM FriendshipEntity f
        WHERE (f.requester.id = :userId OR f.receiver.id = :userId)
        AND f.status = 'ACCEPTED'
    )
    OR p.user.id = :userId
    ORDER BY p.createdAt DESC
    """)
    List<PostEntity> findAllPostsForUserAndFriends(@Param("userId") Long userId);

    @Query("select p from PostEntity p where p.user = :user order by p.createdAt DESC")
    List<PostEntity> findAllPostsByUser(@Param("user") UserEntity user);
}
