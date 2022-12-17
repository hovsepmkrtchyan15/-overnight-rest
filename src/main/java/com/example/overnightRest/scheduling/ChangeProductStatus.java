package com.example.overnightRest.scheduling;

import com.example.common.entity.Status;
import com.example.common.entity.UserBook;
import com.example.common.repository.UserBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor

public class ChangeProductStatus {

    private final UserBookRepository userBookRepository;

    @Scheduled(cron = "0 0  * * * *")
    public void changeStatus() {
        Date date = new Date();
        List<UserBook> userBookByStartDateList = userBookRepository.findUserBookByStartDate(date);
        if (!userBookByStartDateList.isEmpty()) {
            for (UserBook userBook : userBookByStartDateList) {
                userBook.getProduct().setStatus(Status.INACTIVE);
                log.info("Change status INACTIV");
            }
        }
        List<UserBook> userBookByEndDate = userBookRepository.findUserBookByEndDate(date);
        if (!userBookByEndDate.isEmpty()) {
            for (UserBook userBook : userBookByEndDate) {
                userBook.getProduct().setStatus(Status.ACTIVE);
                log.info("Changed status ACTIVE");
            }
        }
    }
}
