package com.testapp.municipalitytax.web.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.testapp.municipalitytax.web.converters.ToLowerCaseConverter;

import java.time.LocalDate;
import java.util.UUID;

public record FullTaxInfo(
        UUID id,
        String municipality,
        Double tax,
        @JsonFormat(pattern = "yyyy.MM.dd") LocalDate startDate,
        @JsonSerialize(converter = ToLowerCaseConverter.class) String schedule) {}
