package com.example.library.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "library_item_view")
@Data
public class LibraryItemView {
    @Id
    private Integer id;

    private String title;
    private String author;
    private LocalDate publishDate;
    private String status;
    private String type;
    private LocalDate returnDate;
    private Integer userId;
    private String borrowedBy;
}