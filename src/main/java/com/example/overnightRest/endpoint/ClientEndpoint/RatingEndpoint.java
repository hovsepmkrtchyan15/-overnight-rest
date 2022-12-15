package com.example.overnightRest.endpoint.ClientEndpoint;

import com.example.common.dto.UserBookDto;
import com.example.overnightRest.exception.EntityNotFoundException;
import com.example.overnightRest.service.UserBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/client")
public class RatingEndpoint {

    private final UserBookService userBookService;

    @PutMapping("/rating")
    public ResponseEntity<?> changeRating(@Valid @RequestBody UserBookDto userBookDto) throws EntityNotFoundException {
        userBookService.changeRating(userBookDto);
        return ResponseEntity.ok().build();
    }
}
