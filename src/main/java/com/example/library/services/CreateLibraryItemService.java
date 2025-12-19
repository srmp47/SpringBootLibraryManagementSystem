package com.example.library.services;

import com.example.library.LibraryManagementSystemApplication;
import com.example.library.database.Database;
import com.example.library.models.*;
import com.example.library.models.dto.BookDTO;
import com.example.library.models.dto.MagazineDTO;
import com.example.library.models.dto.ReferenceDTO;
import com.example.library.models.dto.ThesisDTO;
import com.example.library.models.enums.LibraryItemStatus;
import com.example.library.models.enums.LibraryItemType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateLibraryItemService {
    @Transactional
    public Book createBook(BookDTO bookDTO){
        Book book = Book.builder().title(bookDTO.getTitle())
                .isbn(bookDTO.getIsbn())
                .genre(bookDTO.getGenre())
                .author(bookDTO.getAuthor())
                .pageCount(bookDTO.getPageCount())
                .publishDate(bookDTO.getPublishDate())
                .status(LibraryItemStatus.EXIST)
                .type(LibraryItemType.BOOK)
                .build();
        Database.getLibraryItems().add(book);
        return book;
    }

    @Transactional
    public Thesis createThesis(ThesisDTO thesisDTO){
        Thesis thesis = Thesis.builder().advisor(thesisDTO.getAdvisor())
                .author(thesisDTO.getAuthor())
                .publishDate(thesisDTO.getPublishDate())
                .department(thesisDTO.getDepartment())
                .title(thesisDTO.getTitle())
                .university(thesisDTO.getUniversity())
                .status(LibraryItemStatus.EXIST)
                .type(LibraryItemType.THESIS)
                .build();
        Database.getLibraryItems().add(thesis);
        return thesis;
    }

    @Transactional
    public Magazine createMagazine(MagazineDTO magazineDTO){
        Magazine magazine = Magazine.builder().issueNumber(magazineDTO.getIssueNumber())
                .publishDate(magazineDTO.getPublishDate())
                .title(magazineDTO.getTitle())
                .author(magazineDTO.getAuthor())
                .category(magazineDTO.getCategory())
                .publisher(magazineDTO.getPublisher())
                .status(LibraryItemStatus.EXIST)
                .type(LibraryItemType.MAGAZINE)
                .build();
        Database.getLibraryItems().add(magazine);
        return magazine;
    }

    @Transactional
    public Reference createReference(ReferenceDTO referenceDTO){
        Reference reference = Reference.builder().edition(referenceDTO.getEdition())
                .referenceType(referenceDTO.getReferenceType())
                .author(referenceDTO.getAuthor())
                .publishDate(referenceDTO.getPublishDate())
                .subject(referenceDTO.getSubject())
                .title(referenceDTO.getTitle())
                .status(LibraryItemStatus.EXIST)
                .type(LibraryItemType.REFERENCE)
                .build();
        Database.getLibraryItems().add(reference);
        return reference;
    }
}
