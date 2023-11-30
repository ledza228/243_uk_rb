package com.testapp.municipalitytax.converter.payloadToDomain;

import com.testapp.municipalitytax.domain.MunicipalityTax;
import com.testapp.municipalitytax.domain.Schedule;
import com.testapp.municipalitytax.web.exceptions.ScheduleParsingException;
import com.testapp.municipalitytax.web.payload.UpdateTaxRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class UpdateTaxRequestToMunicipalityTaxConverter
    implements Converter<UpdateTaxRequest, MunicipalityTax> {

  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

  @Override
  public MunicipalityTax convert(UpdateTaxRequest source) {
    Schedule schedule;
    try {
      schedule = Schedule.valueOf(source.schedule().toUpperCase());
    }
    catch (IllegalArgumentException ex){
      throw new ScheduleParsingException();
    }

    return  new MunicipalityTax(
            null,
            null,
            source.tax(),
            LocalDate.parse(source.startDate(), formatter),
            schedule
    );

  }
}
