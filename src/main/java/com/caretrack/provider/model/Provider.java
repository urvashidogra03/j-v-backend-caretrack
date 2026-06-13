package com.caretrack.provider.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;//is used to tell time at whcih that value is created

import java.math.BigDecimal;
import java.time.Instant;
//In a database, instead of scanning every row, an index lets the database quickly locate records.Without an index → Full table scan (slow)
//With an index → Direct lookup (fast)
@Entity
@Table(name = "providers", indexes = {
        @Index(name = "idx_provider_city", columnList = "city"),//Creates an index on the city column.
        @Index(name = "idx_provider_state", columnList = "state"),//SELECT * FROM providers WHERE state = 'Maharashtra';

        @Index(name = "idx_provider_country", columnList = "country"),//Used for country-level filtering.
        @Index(name = "idx_provider_lat_lon", columnList = "latitude, longitude") //SELECT * FROM providers
       // WHERE latitude BETWEEN X AND Y AND longitude BETWEEN A AND B;
        //This helps in location-based search (nearest provider, distance search, etc.)
})
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 180)
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)//This tells JPA how to store the enum in the database of type String.Without this, default = EnumType.ORDINAL

   // DB would store numbers (0, 1, 2...), which is dangerous:Changing enum order breaks data,Harder to understand
    //So using STRING is safer.
    @Column(nullable = false, length = 30)
    private ProviderType type = ProviderType.HOSPITAL;//This field stores an enum inside your entity.default value of provider is HOSPITAL
     //eg| type     | So every provider must have a type, and if not provided, it becomes HOSPITAL.
     // | -------- |
     // | HOSPITAL |
    @Size(max = 180)
    private String addressLine1;

    @Size(max = 180)
    private String addressLine2;

    @Size(max = 100)
    private String city;

    @Size(max = 100)
    private String state;

    @Size(max = 100)
    private String country;

    @Size(max = 20)
    private String postalCode;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    @Size(max = 30)
    private String phone;

    @Email
    @Size(max = 150)
    private String email;

    @Column(nullable = false) //is a database-level constraint that tells the database:“This column cannot be NULL.”
    private boolean active = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public Provider() {}

    // Getters and setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProviderType getType() {
        return type;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(ProviderType type) {
        this.type = type;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
