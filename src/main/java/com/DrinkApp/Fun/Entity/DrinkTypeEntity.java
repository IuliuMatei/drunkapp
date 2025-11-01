package com.DrinkApp.Fun.Entity;

import com.DrinkApp.Fun.Utils.Enums.DrinkName;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "drink_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrinkTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private DrinkName name;

    @Column(nullable = false)
    private Integer points;

}

