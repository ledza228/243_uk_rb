package com.testapp.municipalitytax.web.payload;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

public record UpdateTaxRequest(@DecimalMin("0") @DecimalMax("1") Double tax,
                               String startDate,
                               String schedule) {}
