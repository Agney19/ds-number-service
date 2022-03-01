package ru.dsnumberservice.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.dsnumberservice.dal.NumDao;
import ru.dsnumberservice.exception.IllegalNumException;
import ru.dsnumberservice.web.dto.NumDto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class NumService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NumDao.class);

    private final NumDao dao;

    @Value("${num-service.timeout-ms}")
    private long timeout;

    public NumDto process(NumDto numDto) {
        int value = numDto.getValue();
        assertValidAndSave(value, LocalDateTime.now());
        return new NumDto(value + 1);
    }

    private void assertValidAndSave(int value, LocalDateTime start) {
        if (start.plus(timeout, ChronoUnit.MILLIS).isBefore(LocalDateTime.now())) {
            throw new IllegalNumException("Request unsuccessful due to timeout");
        }
        try {
            dao.assertValidAndSave(value);
        } catch (DataAccessException e) {
            LOGGER.info("Encountered exception during db access: " + e.getMessage() + ". So next try will be undertaken");
            assertValidAndSave(value, start);
        }
    }
}
