package com.testapp.municipalitytax.converter.domainToPayload;

import com.testapp.municipalitytax.domain.MunicipalityTax;
import com.testapp.municipalitytax.web.payload.TaxResponse;

import java.util.ArrayList;
import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MunicipalityTaxListToTaxResponseConverter
    implements Converter<List<MunicipalityTax>, TaxResponse> {

  @Override
  public TaxResponse convert(List<MunicipalityTax> source) {
    List<Double> res = source
            .stream()
            .map(MunicipalityTax::tax)
            .toList();

    return new TaxResponse(res);
  }
}
