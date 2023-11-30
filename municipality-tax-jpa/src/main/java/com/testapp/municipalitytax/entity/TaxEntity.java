package com.testapp.municipalitytax.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "taxes")
public class TaxEntity {

  @Id
  @GeneratedValue
  private UUID id;

  @Column(name = "municipality")
  private String municipality;

  @Column(name = "tax")
  private Double tax;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;


  public TaxEntity(
      UUID id, String municipality, Double tax, LocalDate startDate, LocalDate endDate) {
    this.id = id;
    this.municipality = municipality;
    this.tax = tax;
    this.startDate = startDate;
    this.endDate = endDate;
  }


  public TaxEntity(TaxEntity taxEntity, String municipality) {
      this.id = taxEntity.id;
      this.municipality = municipality;
      this.tax = taxEntity.getTax();
      this.startDate = taxEntity.getStartDate();
      this.endDate = taxEntity.endDate;
  }

  public TaxEntity() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getMunicipality() {
    return municipality;
  }

  public void setMunicipality(String municipality) {
    this.municipality = municipality;
  }

  public Double getTax() {
    return tax;
  }

  public void setTax(Double tax) {
    this.tax = tax;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TaxEntity tax1 = (TaxEntity) o;

    if (!Objects.equals(id, tax1.id)) return false;
    if (!Objects.equals(municipality, tax1.municipality)) return false;
    if (!Objects.equals(tax, tax1.tax)) return false;
    if (!Objects.equals(startDate, tax1.startDate)) return false;
    return Objects.equals(endDate, tax1.endDate);
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (municipality != null ? municipality.hashCode() : 0);
    result = 31 * result + (tax != null ? tax.hashCode() : 0);
    result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
    result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
    return result;
  }
}
