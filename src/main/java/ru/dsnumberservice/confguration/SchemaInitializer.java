package ru.dsnumberservice.confguration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchemaInitializer implements InitializingBean {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void afterPropertiesSet() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS numbers ( " +
                "id serial not null constraint numbers_pkey primary key, " +
                "value numeric not null " +
                ")");
    }
}
