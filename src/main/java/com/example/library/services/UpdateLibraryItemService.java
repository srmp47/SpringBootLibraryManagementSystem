package com.example.library.services;

import com.example.library.exceptions.BusinessRuleException;
import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.models.LibraryItem;
import com.example.library.models.User;
import com.example.library.models.enums.LibraryItemStatus;
import com.example.library.repositories.LibraryItemRepository;
import com.example.library.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateLibraryItemService {

    private final LibraryItemRepository libraryItemRepository;
    private final UserRepository userRepository;

    @Transactional
    public LibraryItem patchLibraryItemStatus(int id, LibraryItemStatus newStatus) {
        if (newStatus == LibraryItemStatus.BORROWED) {
            throw new BusinessRuleException("Changing status to BORROWED is not allowed.");
        }
        LibraryItem libraryItem = findItemOrThrow(id);
        libraryItem.setStatus(newStatus);
        return libraryItemRepository.save(libraryItem);
    }

    @Transactional
    public LibraryItem updateLibraryItemTitle(int id, String newTitle) {
        LibraryItem libraryItem = findItemOrThrow(id);
        libraryItem.setTitle(newTitle);
        return libraryItemRepository.save(libraryItem);
    }

    @Transactional
    public LibraryItem updateLibraryItemAuthor(int id, String newAuthor) {
        LibraryItem libraryItem = findItemOrThrow(id);
        libraryItem.setAuthor(newAuthor);
        return libraryItemRepository.save(libraryItem);
    }

    @Transactional
    public LibraryItem borrowItem(int id, Integer userId) {
        LibraryItem libraryItem = findItemOrThrow(id);

        if (libraryItem.getUser() != null) {
            throw new BusinessRuleException("This item is already borrowed by another user.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        libraryItem.setUser(user);
        libraryItem.setStatus(LibraryItemStatus.BORROWED);
        return libraryItemRepository.save(libraryItem);
    }

    @Transactional
    public LibraryItem returnItem(int id) {
        LibraryItem libraryItem = findItemOrThrow(id);
        libraryItem.setUser(null);
        libraryItem.setReturnDate(null);
        libraryItem.setStatus(LibraryItemStatus.EXIST);
        return libraryItemRepository.save(libraryItem);
    }

    private LibraryItem findItemOrThrow(int id) {
        return libraryItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));
    }
}