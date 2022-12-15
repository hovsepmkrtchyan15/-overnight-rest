package com.example.overnightRest.service;


import com.example.common.dto.UserBookDto;
import com.example.common.entity.Product;
import com.example.common.entity.UserBook;
import com.example.common.repository.ProductRepository;
import com.example.common.repository.UserBookRepository;
import com.example.overnightRest.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
public class UserBookService {
    private final UserBookRepository userBookRepository;
    private final ProductRepository productRepository;

    public void changeRating(UserBookDto userBookDto) throws EntityNotFoundException {
        Optional<UserBook> userBook = userBookRepository.findUserBooksByUserIdAndProductId(userBookDto.getUser().getId(), userBookDto.getProduct().getId());
        if (userBook.isEmpty()) {
            throw new EntityNotFoundException("UserBook whit userId = " + userBookDto.getUser().getId()+
                    "and product id = " + userBookDto.getProduct().getId() + " does not exists");
        }
        Optional<Product> byId = productRepository.findById(userBookDto.getProduct().getId());
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Product whit id = " + userBookDto.getProduct().getId()+ " does not exists");
        }
        int rating = (byId.get().getRating() + userBookDto.getRating());
        int count = byId.get().getRatingUserCount() + 1;
        byId.get().setRating(rating /2);
        byId.get().setRatingUserCount(count);
        productRepository.save(byId.get());
        log.info("Rating product id = " + byId.get().getId() + " was updated by a client email = " + userBook.get().getUser().getEmail());
    }

}




