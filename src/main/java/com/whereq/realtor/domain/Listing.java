package com.whereq.realtor.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.whereq.realtor.domain.enumeration.ListingType;

import com.whereq.realtor.domain.enumeration.ListingStatus;

import com.whereq.realtor.domain.enumeration.Style;

/**
 * A Listing.
 */
@Entity
@Table(name = "listing")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Listing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "listing_type", nullable = false)
    private ListingType listingType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ListingStatus status;

    @NotNull
    @Column(name = "input_date", nullable = false)
    private LocalDate inputDate;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "style", nullable = false)
    private Style style;

    @NotNull
    @Column(name = "listing_price", precision=10, scale=2, nullable = false)
    private BigDecimal listingPrice;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Location location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ListingType getListingType() {
        return listingType;
    }

    public Listing listingType(ListingType listingType) {
        this.listingType = listingType;
        return this;
    }

    public void setListingType(ListingType listingType) {
        this.listingType = listingType;
    }

    public ListingStatus getStatus() {
        return status;
    }

    public Listing status(ListingStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ListingStatus status) {
        this.status = status;
    }

    public LocalDate getInputDate() {
        return inputDate;
    }

    public Listing inputDate(LocalDate inputDate) {
        this.inputDate = inputDate;
        return this;
    }

    public void setInputDate(LocalDate inputDate) {
        this.inputDate = inputDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public Listing modifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Style getStyle() {
        return style;
    }

    public Listing style(Style style) {
        this.style = style;
        return this;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public BigDecimal getListingPrice() {
        return listingPrice;
    }

    public Listing listingPrice(BigDecimal listingPrice) {
        this.listingPrice = listingPrice;
        return this;
    }

    public void setListingPrice(BigDecimal listingPrice) {
        this.listingPrice = listingPrice;
    }

    public Location getLocation() {
        return location;
    }

    public Listing location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Listing listing = (Listing) o;
        if (listing.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, listing.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Listing{" +
            "id=" + id +
            ", listingType='" + listingType + "'" +
            ", status='" + status + "'" +
            ", inputDate='" + inputDate + "'" +
            ", modifiedDate='" + modifiedDate + "'" +
            ", style='" + style + "'" +
            ", listingPrice='" + listingPrice + "'" +
            '}';
    }
}
