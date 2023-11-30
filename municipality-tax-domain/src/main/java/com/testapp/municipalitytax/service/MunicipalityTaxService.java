package com.testapp.municipalitytax.service;

import com.testapp.municipalitytax.domain.MunicipalityTax;
import com.testapp.municipalitytax.domain.TaxesRepository;
import com.testapp.municipalitytax.web.TaxesService;
import com.testapp.municipalitytax.web.payload.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class MunicipalityTaxService implements TaxesService {

  private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

  private final TaxesRepository taxesRepository;
  private final ConversionService conversionService;

  public MunicipalityTaxService(
      TaxesRepository taxesRepository, ConversionService conversionService) {
    this.taxesRepository = taxesRepository;
    this.conversionService = conversionService;
  }

  @Override
  public UUIDResponse addTax(AddTaxRequest addTaxRequest) {
    MunicipalityTax tax = conversionService.convert(addTaxRequest, MunicipalityTax.class);

    tax = taxesRepository.save(tax);
    return new UUIDResponse(tax.id());
  }

  @Override
  public void updateTax(UUID taxId, UpdateTaxRequest updateTaxRequest) {
    MunicipalityTax newTax = conversionService.convert(updateTaxRequest, MunicipalityTax.class);
    newTax = newTax.withId(taxId);

    taxesRepository.update(newTax);
  }

  @Override
  public TaxResponse findTax(String municipality, String date) {
    List<MunicipalityTax> taxes = taxesRepository.findByMunicipalityAndDate(municipality, LocalDate.parse(date, formatter));

    List<Double> taxValues = taxes.stream()
            .map(MunicipalityTax::tax)
            .toList();

    return new TaxResponse(taxValues);
  }

  @Override
  public TaxListResponse getAllMunicipalityTaxes() {
    List<FullTaxInfo> municipalityTaxes = taxesRepository.getAllMunicipalityTaxes()
            .stream()
            .map(tax -> conversionService.convert(tax, FullTaxInfo.class))
            .toList();

    return new TaxListResponse(municipalityTaxes.size(), municipalityTaxes);
  }
}
