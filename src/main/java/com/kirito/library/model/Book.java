package com.kirito.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID bookId;

    private String title;

    private String author;

    private Integer date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Person owner;

    @Column(name = "taken_at")
    private LocalDateTime takenAt;
}
