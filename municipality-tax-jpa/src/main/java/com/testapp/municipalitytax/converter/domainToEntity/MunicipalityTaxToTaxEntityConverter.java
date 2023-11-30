package com.testapp.municipalitytax.converter.domainToEntity;

import com.testapp.municipalitytax.domain.MunicipalityTax;
import com.testapp.municipalitytax.domain.Schedule;
import com.testapp.municipalitytax.entity.TaxEntity;
import java.time.LocalDate;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MunicipalityTaxToTaxEntityConverter implements Converter<MunicipalityTax, TaxEntity> {
  @Override
  public TaxEntity convert(MunicipalityTax source) {
    TaxEntity tax = new TaxEntity(source.id(),
            source.municipality(),
            source.tax(),
            source.startDate(),
            scheduleToDate(source.startDate(), source.taxSchedule()));

    return tax;
  }

  private LocalDate scheduleToDate(LocalDate startDate, Schedule schedule) {
    return switch (schedule) {
      case YEARLY -> startDate.plusYears(1).minusDays(1);
      case MONTHLY -> startDate.plusMonths(1).minusDays(1);
      case WEEKLY -> startDate.plusWeeks(1).minusDays(1);
      case DAILY -> startDate;
    };
  }
}
