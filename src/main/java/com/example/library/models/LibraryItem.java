package com.example.library.models;

import com.example.library.models.enums.LibraryItemStatus;
import com.example.library.models.enums.LibraryItemType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;

@Entity
@Table(name = "library_item")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class LibraryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false)
    protected String title;

    protected String author;

    protected LocalDate publishDate;

    @Enumerated(EnumType.STRING)
    protected LibraryItemStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    protected LibraryItemType type;

    protected LocalDate returnDate;
}