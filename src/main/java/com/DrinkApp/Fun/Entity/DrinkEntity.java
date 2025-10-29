package com.DrinkApp.Fun.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "drinks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "photo_id", nullable = false)
    private PhotoEntity photo;

    @ManyToOne
    @JoinColumn(name = "drink_type_id", nullable = false)
    private DrinkTypeEntity drinkType;

    private Integer volumeMl;
}

