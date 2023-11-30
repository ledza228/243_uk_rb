package com.testapp.municipalitytax.repository;

import com.testapp.municipalitytax.entity.TaxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaxesJpaRepository
    extends JpaRepository<TaxEntity, UUID> {

    List<TaxEntity> findAllByMunicipalityAndStartDate(String municipal, LocalDate date);

}
