package com.testapp.municipalitytax.web.payload;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

public record AddTaxRequest(String municipality,
                            @DecimalMin("0") @DecimalMax("1") Double tax,
                            @JsonFormat(pattern = "yyyy.MM.dd") String startDate,
                            String schedule) {}
