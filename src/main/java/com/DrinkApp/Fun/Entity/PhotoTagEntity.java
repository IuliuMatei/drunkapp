package com.DrinkApp.Fun.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "photo_tags", uniqueConstraints = @UniqueConstraint(columnNames = {"photo_id", "tagged_user_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "photo_id", nullable = false)
    private PhotoEntity photo;

    @ManyToOne
    @JoinColumn(name = "tagged_user_id", nullable = false)
    private UserEntity taggedUser;
}

