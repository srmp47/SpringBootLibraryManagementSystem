package com.example.library.services;

import com.example.library.exceptions.DuplicateResourceException;
import com.example.library.models.*;
import com.example.library.models.dto.*;
import com.example.library.models.enums.*;
import com.example.library.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateLibraryItemService {

    private final BookRepository bookRepository;
    private final MagazineRepository magazineRepository;
    private final ReferenceRepository referenceRepository;
    private final ThesisRepository thesisRepository;
    private final LibraryItemRepository libraryItemRepository;

    @Transactional
    public Book createBook(BookDTO bookDTO) {
        if (bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new DuplicateResourceException("Book with ISBN " + bookDTO.getIsbn() + " already exists");
        }

        Book book = Book.builder()
                .title(bookDTO.getTitle())
                .isbn(bookDTO.getIsbn())
                .genre(bookDTO.getGenre())
                .author(bookDTO.getAuthor())
                .pageCount(bookDTO.getPageCount())
                .publishDate(bookDTO.getPublishDate())
                .status(LibraryItemStatus.EXIST)
                .type(LibraryItemType.BOOK)
                .version(0)
                .build();

        log.info("Creating book: {}", book.getTitle());
        return bookRepository.save(book);
    }

    @Transactional
    public Thesis createThesis(ThesisDTO thesisDTO) {
        boolean exists = libraryItemRepository.findByTitleContainingIgnoreCase(thesisDTO.getTitle())
                .stream()
                .anyMatch(item -> item.getAuthor() != null &&
                        item.getAuthor().equalsIgnoreCase(thesisDTO.getAuthor()) &&
                        item.getType() == LibraryItemType.THESIS);

        if (exists) {
            throw new DuplicateResourceException("Thesis with same title and author already exists");
        }

        Thesis thesis = Thesis.builder()
                .advisor(thesisDTO.getAdvisor())
                .author(thesisDTO.getAuthor())
                .publishDate(thesisDTO.getPublishDate())
                .department(thesisDTO.getDepartment())
                .title(thesisDTO.getTitle())
                .university(thesisDTO.getUniversity())
                .status(LibraryItemStatus.EXIST)
                .type(LibraryItemType.THESIS)
                .version(0)
                .build();

        log.info("Creating thesis: {}", thesis.getTitle());
        return thesisRepository.save(thesis);
    }

    @Transactional
    public Magazine createMagazine(MagazineDTO magazineDTO) {
        magazineRepository.findByIssueNumber(magazineDTO.getIssueNumber())
                .ifPresent(m -> {
                    throw new DuplicateResourceException("Magazine with issue number " + magazineDTO.getIssueNumber() + " already exists");
                });

        Magazine magazine = Magazine.builder()
                .issueNumber(magazineDTO.getIssueNumber())
                .publishDate(magazineDTO.getPublishDate())
                .title(magazineDTO.getTitle())
                .author(magazineDTO.getAuthor())
                .category(magazineDTO.getCategory())
                .publisher(magazineDTO.getPublisher())
                .status(LibraryItemStatus.EXIST)
                .type(LibraryItemType.MAGAZINE)
                .version(0)
                .build();

        log.info("Creating magazine: {}", magazine.getTitle());
        return magazineRepository.save(magazine);
    }

    @Transactional
    public Reference createReference(ReferenceDTO referenceDTO) {
        Reference reference = Reference.builder()
                .edition(referenceDTO.getEdition())
                .referenceType(referenceDTO.getReferenceType())
                .author(referenceDTO.getAuthor())
                .publishDate(referenceDTO.getPublishDate())
                .subject(referenceDTO.getSubject())
                .title(referenceDTO.getTitle())
                .status(LibraryItemStatus.EXIST)
                .type(LibraryItemType.REFERENCE)
                .version(0)
                .build();

        log.info("Creating reference: {}", reference.getTitle());
        return referenceRepository.save(reference);
    }
}