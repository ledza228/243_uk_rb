package com.testapp.municipalitytax.repository;

import com.testapp.municipalitytax.domain.MunicipalityTax;
import com.testapp.municipalitytax.domain.TaxesRepository;
import com.testapp.municipalitytax.entity.TaxEntity;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Component
public class MunicipalityTaxesRepository implements TaxesRepository {

  private final Logger logger = Logger.getLogger("MunicipalityTaxesRepository");

  private final TaxesJpaRepository taxesJpaRepository;
  private final ConversionService conversionService;

  public MunicipalityTaxesRepository(
      TaxesJpaRepository taxesJpaRepository, ConversionService conversionService) {
    this.taxesJpaRepository = taxesJpaRepository;
    this.conversionService = conversionService;
  }

  @Override
  public MunicipalityTax save(MunicipalityTax municipalityTax) {
    TaxEntity tax = conversionService.convert(municipalityTax, TaxEntity.class);
    tax = taxesJpaRepository.save(tax);
    logger.info("saved new tax: " + tax.getId());

    return conversionService.convert(tax, MunicipalityTax.class);
  }

  @Override
  public int update(MunicipalityTax municipalityTax) {
    TaxEntity oldEntity = taxesJpaRepository.findById(municipalityTax.id())
//            .orElseThrow(() -> new RuntimeException("not founded id: " + municipalityTax.id()));  it was a good choice...
            .orElse(null);

    String municipality = "WTF I SHOULD PLACE HERE???";
    if (oldEntity != null){
      municipality = oldEntity.getMunicipality();
    }

    TaxEntity newEntity = conversionService.convert(municipalityTax, TaxEntity.class);
    newEntity = new TaxEntity(newEntity, municipality);
    newEntity = taxesJpaRepository.save(newEntity);
    logger.info("updated tax: " + newEntity.getId());

    return oldEntity == null ? 0 : 1;
  }

  @Override
  public List<MunicipalityTax> findByMunicipalityAndDate(String municipality, LocalDate date) {
    List<TaxEntity> taxes = taxesJpaRepository.findAllByMunicipalityAndStartDate(municipality, date);
    return taxesJpaRepository.findAll()
            .stream()
            .filter((tax) -> tax.getMunicipality().equals(municipality) && tax.getStartDate().equals(date))
            .map((tax) -> conversionService.convert(tax, MunicipalityTax.class))
            .toList();
  }

  @Override
  public List<MunicipalityTax> getAllMunicipalityTaxes() {
    List<TaxEntity> taxEntities = taxesJpaRepository.findAll();

    return taxEntities
            .stream()
            .map(tax -> conversionService.convert(tax, MunicipalityTax.class))
            .toList();
  }
}
