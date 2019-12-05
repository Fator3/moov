package com.fator3.moov.property;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

@Entity(name="property")
public class PersistentProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String street;
    private String number;

    @Column(name = "reachable_range")
    private Polygon reachableRange;
    private Point location;

    @Column(name = "active")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean active = true;

    @CreationTimestamp
    @Column(name = "created_timestamp", updatable = false)
    private LocalDateTime createdTimestamp;

    @UpdateTimestamp
    @Column(name = "updated_timestamp")
    private LocalDateTime updatedTimestamp;

    PersistentProperty() {
    }

    private PersistentProperty(String street, String number, Point location,
            Polygon reachableRange) {
        this.street = street;
        this.number = number;
        this.location = location;
        this.reachableRange = reachableRange;
    }

    public static PersistentProperty of(String street, String number, Point location,
            Polygon reachableRange) {
        return new PersistentProperty(street, number, location, reachableRange);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Polygon getReachableRange() {
        return reachableRange;
    }

    public void setReachableRange(Polygon reachableRange) {
        this.reachableRange = reachableRange;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public LocalDateTime getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(LocalDateTime updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof PersistentProperty))
            return false;
        final PersistentProperty card = (PersistentProperty) obj;
        return Objects.equals(getId(), card.getId());
    }

}
