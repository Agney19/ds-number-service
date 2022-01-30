package ru.dsnumberservice.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dsnumberservice.exception.IllegalNumException;
import ru.dsnumberservice.service.NumService;
import ru.dsnumberservice.web.dto.NumDto;

import static ru.dsnumberservice.exception.IllegalNumException.OUT_OF_BOUNDS_VALUE_ERROR_MSG;

@RestController
public class NumController {

    private final NumService service;
    private final int maxValue;

    private NumController(NumService service, @Value("${num-service.max-value}") int maxValue) {
        this.service = service;

        if (maxValue < 0 || maxValue > Integer.MAX_VALUE - 1) {
            throw new IllegalNumException(String.format(OUT_OF_BOUNDS_VALUE_ERROR_MSG, 0, Integer.MAX_VALUE - 1));
        }
        this.maxValue = maxValue;
    }

    @PostMapping("job")
    public NumDto process(@RequestBody NumDto dto) {
        validate(dto);
        return service.process(dto);
    }

    private void validate(NumDto dto) {
        int value = dto.getValue();

        if (value < 0 || value > maxValue) {
            throw new IllegalNumException(String.format(OUT_OF_BOUNDS_VALUE_ERROR_MSG, 0, maxValue));
        }
    }
}
