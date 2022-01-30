package ru.dsnumberservice.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.dsnumberservice.dal.NumDao;
import ru.dsnumberservice.web.dto.NumDto;

@Service
@RequiredArgsConstructor
public class NumService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NumDao.class);

    private final NumDao dao;

    public NumDto process(NumDto numDto) {
        int value = numDto.getValue();
        assertValidAndSave(value);
        return new NumDto(value + 1);
    }

    private void assertValidAndSave(int value) {
        try {
            dao.assertValidAndSave(value);
        } catch (DataAccessException e) {
            LOGGER.debug("Encountered exception during db access: " + e.getCause() + "\nSo next try will be undertaken");
            assertValidAndSave(value);
        }
    }
}
