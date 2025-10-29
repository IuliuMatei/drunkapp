package com.DrinkApp.Fun.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_tags", uniqueConstraints = @UniqueConstraint(columnNames = {"post_id", "tagged_user_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @ManyToOne
    @JoinColumn(name = "tagged_user_id", nullable = false)
    private UserEntity taggedUser;
}

