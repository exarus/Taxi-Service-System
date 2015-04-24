package ua.com.tracksee.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Ruslan Gunavardana
 */
@Entity
@Table(name = "taxi_price", schema = "public", catalog = "tracksee")
@IdClass(TaxiPriceEntityPK.class)
public class TaxiPriceEntity {
    private BigDecimal pricePerKm;
    private BigDecimal pricePerMin;
    private String carCategory;
    private Boolean weekend;
    private Boolean nightTariff;

    @Basic
    @Column(name = "price_per_km")
    public BigDecimal getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(BigDecimal pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    @Basic
    @Column(name = "price_per_min")
    public BigDecimal getPricePerMin() {
        return pricePerMin;
    }

    public void setPricePerMin(BigDecimal pricePerMin) {
        this.pricePerMin = pricePerMin;
    }

    @Id
    @Column(name = "car_category")
    public String getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(String carCategory) {
        this.carCategory = carCategory;
    }

    public Boolean isWeekend() {
        return weekend;
    }

    public void setWeekend(Boolean weekend) {
        this.weekend = weekend;
    }

    public Boolean isNightTariff() {
        return nightTariff;
    }

    public void setNightTariff(Boolean nightTariff) {
        this.nightTariff = nightTariff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxiPriceEntity that = (TaxiPriceEntity) o;
        return Objects.equals(pricePerKm, that.pricePerKm) &&
                Objects.equals(pricePerMin, that.pricePerMin) &&
                Objects.equals(carCategory, that.carCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pricePerKm, pricePerMin, carCategory);
    }
}
