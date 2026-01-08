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
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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
            throw new BusinessRuleException("Changing status to BORROWED is not allowed. Use borrow endpoint instead.");
        }

        LibraryItem libraryItem = findItemOrThrow(id);
        libraryItem.setStatus(newStatus);

        try {
            return libraryItemRepository.save(libraryItem);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new BusinessRuleException("This item was modified by another user. Please refresh and try again.");
        }
    }

    @Transactional
    public LibraryItem borrowItem(int itemId, Integer userId) {
        LibraryItem libraryItem = libraryItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + itemId));

        if (libraryItem.getUser() != null) {
            throw new BusinessRuleException("This item is already borrowed by another user.");
        }

        if (libraryItem.getStatus() != LibraryItemStatus.EXIST) {
            throw new BusinessRuleException("This item is not available for borrowing.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));


        long borrowedCount = libraryItemRepository.findItemsByUserId(userId).size();
        if (borrowedCount >= 5) {
            throw new BusinessRuleException("User cannot borrow more than 5 items.");
        }

        libraryItem.setUser(user);
        libraryItem.setStatus(LibraryItemStatus.BORROWED);

        log.info("User {} borrowed item {}", userId, itemId);
        return libraryItemRepository.save(libraryItem);
    }

    @Transactional
    public LibraryItem returnItem(int itemId, int userId) {
        LibraryItem libraryItem = findItemOrThrow(itemId);

        if (libraryItem.getUser() == null) {
            throw new BusinessRuleException("This item is not borrowed.");
        }
        if( libraryItem.getUser().getId() != userId) {
            throw new BusinessRuleException("This item was borrowed by another user.");
        }

        libraryItem.setUser(null);
        libraryItem.setStatus(LibraryItemStatus.EXIST);
        libraryItem.setReturnDate(null);

        log.info("Item {} returned", itemId);
        return libraryItemRepository.save(libraryItem);
    }

    private LibraryItem findItemOrThrow(int id) {
        return libraryItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));
    }
}