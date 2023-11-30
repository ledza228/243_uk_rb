package com.testapp.municipalitytax.repository;

import com.testapp.municipalitytax.TestDataFactory;
import com.testapp.municipalitytax.domain.MunicipalityTax;
import com.testapp.municipalitytax.entity.TaxEntity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.core.convert.ConversionService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class MunicipalityTaxesRepositoryUnitTest {

  private MunicipalityTaxesRepository municipalityTaxesRepository;

  @Mock private TaxesJpaRepository taxesJpaRepository;

  @Mock private ConversionService conversionService;

  @Before
  public void setUp() {
    openMocks(this);
    municipalityTaxesRepository =
        new MunicipalityTaxesRepository(taxesJpaRepository, conversionService);
    when(conversionService.convert(any(TaxEntity.class), eq(MunicipalityTax.class)))
        .thenReturn(TestDataFactory.createSavedMunicipalityTax());
  }

  @Test
  public void shouldCallJpaRepositoryAndReturnMunicipalityTax_whenSave_givenMunicipalityTax() {
    // given
    TaxEntity entity = TestDataFactory.createTaxEntity();
    MunicipalityTax municipalityTax = TestDataFactory.createMunicipalityTax();
    MunicipalityTax expected = TestDataFactory.createSavedMunicipalityTax();
    when(conversionService.convert(any(MunicipalityTax.class), eq(TaxEntity.class)))
        .thenReturn(entity);
    when(taxesJpaRepository.save(any(TaxEntity.class))).thenReturn(entity);

    // when
    MunicipalityTax result = municipalityTaxesRepository.save(municipalityTax);

    // then
    verify(conversionService, times(1)).convert(municipalityTax, TaxEntity.class);
    verify(conversionService, times(1)).convert(entity, MunicipalityTax.class);
    verify(taxesJpaRepository, times(1)).save(entity);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void shouldCallJpaRepositoryAndReturnInt_whenUpdate_givenExistingMunicipalityTax() {
    // given
    TaxEntity entity = TestDataFactory.createTaxEntity();
    MunicipalityTax municipalityTax = TestDataFactory.createSavedMunicipalityTax();
    when(conversionService.convert(any(MunicipalityTax.class), eq(TaxEntity.class)))
        .thenReturn(entity);
    when(taxesJpaRepository.findById(any())).thenReturn(Optional.of(entity));
    when(taxesJpaRepository.save(any(TaxEntity.class))).thenReturn(entity);

    // when
    int result = municipalityTaxesRepository.update(municipalityTax);

    // then
    verify(taxesJpaRepository, times(1)).findById(municipalityTax.id());
    verify(conversionService, times(1)).convert(municipalityTax, TaxEntity.class);
    verify(taxesJpaRepository, times(1)).save(entity);
    assertThat(result).isEqualTo(1);
  }

  @Test
  public void shouldCallJpaRepositoryAndReturnZeroInt_whenUpdate_givenNotExistingMunicipalityTax() {
    // given
    TaxEntity entity = TestDataFactory.createTaxEntity();
    MunicipalityTax municipalityTax = TestDataFactory.createSavedMunicipalityTax();
    when(conversionService.convert(any(MunicipalityTax.class), eq(TaxEntity.class)))
        .thenReturn(entity);
    when(taxesJpaRepository.findById(any())).thenReturn(Optional.empty()); // let's update if not exists! but what update??
    when(taxesJpaRepository.save(any(TaxEntity.class))).thenReturn(entity);

    // when
    int result = municipalityTaxesRepository.update(municipalityTax);

    // then
    verify(taxesJpaRepository, times(1)).findById(municipalityTax.id());
    assertThat(result).isEqualTo(0);
  }

  @Test
  public void
      shouldCallJpaRepositoryAndReturnMunicipalityTaxList_whenFindByMunicipalityAndDate_givenMunicipalityAndDate() {
    // given
    String municipality = TestDataFactory.municipality;
    LocalDate date = TestDataFactory.date;
    TaxEntity entity = TestDataFactory.createTaxEntity();
    List<MunicipalityTax> expected =
        Collections.singletonList(TestDataFactory.createSavedMunicipalityTax());
    when(taxesJpaRepository.findAll()) // <- nice method calling, sql WHERE clause for loxov))
        .thenReturn(Collections.singletonList(entity));

    // when
    List<MunicipalityTax> result =
        municipalityTaxesRepository.findByMunicipalityAndDate(municipality, date);

    // then
    verify(conversionService, times(1)).convert(entity, MunicipalityTax.class);
    verify(taxesJpaRepository, times(1)).findAll();
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  public void
      shouldCallJpaRepositoryAndReturnMunicipalityTaxList_whenGetAllMunicipalityTaxes_givenNoParams() {
    // given no params
    TaxEntity entity = TestDataFactory.createTaxEntity();
    List<MunicipalityTax> expected =
        Collections.singletonList(TestDataFactory.createSavedMunicipalityTax());
    when(taxesJpaRepository.findAll()).thenReturn(Collections.singletonList(entity));

    // when
    List<MunicipalityTax> result = municipalityTaxesRepository.getAllMunicipalityTaxes();

    // then
    verify(conversionService, times(1)).convert(entity, MunicipalityTax.class);
    verify(taxesJpaRepository, times(1)).findAll();
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }
}
