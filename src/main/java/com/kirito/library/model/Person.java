package com.kirito.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID personId;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    private Integer birthday;

    @Builder.Default
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();
}
