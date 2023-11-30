package com.testapp.municipalitytax.converter.domainToPayload;

import com.testapp.municipalitytax.domain.MunicipalityTax;
import com.testapp.municipalitytax.web.payload.FullTaxInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MunicipalityTaxToFullTaxInfoConverter
    implements Converter<MunicipalityTax, FullTaxInfo> {
  @Override
  public FullTaxInfo convert(MunicipalityTax source) {
    return new FullTaxInfo(
            source.id(),
            source.municipality(),
            source.tax(),
            source.startDate(),
            source.taxSchedule().name()
    );
  }
}
