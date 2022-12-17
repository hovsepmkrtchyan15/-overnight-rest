package com.example.overnightRest.service;


import com.example.common.dto.UserBookDto;
import com.example.common.dto.UserBookOrderDto;
import com.example.common.dto.UserBookOrderSaveDto;
import com.example.common.entity.Product;
import com.example.common.entity.UserBook;
import com.example.common.repository.ProductRepository;
import com.example.common.repository.UserBookRepository;
import com.example.overnightRest.exception.EntityNotFoundException;
import com.example.overnightRest.mapper.UserBookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.overnightRest.util.DateUtil.stringToDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserBookService {
    private final UserBookRepository userBookRepository;
    private final ProductRepository productRepository;
    private final UserBookMapper userBookMapper;

    /**
     *
     * @param userBookDto UserBookDto{User, Product, rating}
     * @throws EntityNotFoundException ExceptionHandler
     */
    public void changeRating(UserBookDto userBookDto) throws EntityNotFoundException {
        Optional<UserBook> userBook = userBookRepository.findUserBooksByUserIdAndProductId(userBookDto.getUser().getId(), userBookDto.getProduct().getId());
        if (userBook.isEmpty()) {
            throw new EntityNotFoundException("UserBook whit userId = " + userBookDto.getUser().getId() +
                    "and product id = " + userBookDto.getProduct().getId() + " does not exists");
        }
        Optional<Product> byId = productRepository.findById(userBookDto.getProduct().getId());
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Product whit id = " + userBookDto.getProduct().getId() + " does not exists");
        }
        int rating = (byId.get().getRating() + userBookDto.getRating());
        int count = byId.get().getRatingUserCount() + 1;
        byId.get().setRating(rating / 2);
        byId.get().setRatingUserCount(count);
        productRepository.save(byId.get());
        log.info("Rating product id = {} was updated by a client email = {}", byId.get().getId(), userBook.get().getUser().getEmail());
    }

    /**
     *
     * @param userBookOrderDto UserBookOrderDto{Product, User, start and end dates}
     * @return UserBook userBook
     * @throws EntityNotFoundException ExceptionHandler
     */
    public UserBookOrderSaveDto booking(UserBookOrderDto userBookOrderDto) throws EntityNotFoundException {

        Date from = stringToDate(userBookOrderDto.getStartDate());
        Date to = stringToDate(userBookOrderDto.getStartDate());
        Optional<List<UserBook>> userBookOrders = userBookRepository.findUserBookOrders(from, to);
        if (userBookOrders.isPresent() && userBookOrders.get().size() != 0) {
            throw new EntityNotFoundException("busy on the indicated dates");
        }
        UserBook userBook = UserBook.builder()
                .startDate(from)
                .endDate(to)
                .user(userBookOrderDto.getUser())
                .product(userBookOrderDto.getProduct())
                .build();
        return userBookMapper.map(userBookRepository.save(userBook));
    }
}



