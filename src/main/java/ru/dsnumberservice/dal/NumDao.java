package ru.dsnumberservice.dal;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.dsnumberservice.exception.IllegalNumException;

import static ru.dsnumberservice.exception.IllegalNumException.SAME_VALUE_ERROR_MSG_TEMPLATE;
import static ru.dsnumberservice.exception.IllegalNumException.VALUE_MINUS_ONE_ERROR_MSG_TEMPLATE;

@Component
@RequiredArgsConstructor
public class NumDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(NumDao.class);

    private final JdbcTemplate jdbcTemplate;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public synchronized void assertValidAndSave(int value) throws DataAccessException {
        jdbcTemplate.query(String.format("select * from numbers where value = %s or value = %s order by value", value, value + 1),
                (rs, rowNum) -> {
                    int storedValue = rs.getInt("value");
                    if (storedValue == value) {
                        String msg = String.format(SAME_VALUE_ERROR_MSG_TEMPLATE, value);
                        LOGGER.error(msg);
                        throw new IllegalNumException(msg);
                    }
                    if (storedValue == value + 1) {
                        String msg = String.format(VALUE_MINUS_ONE_ERROR_MSG_TEMPLATE, value);
                        LOGGER.error(msg);
                        throw new IllegalNumException(msg);
                    }
                    return storedValue;
                });
        jdbcTemplate.execute(String.format("insert into numbers (value) values (%s)", value));
    }
}
