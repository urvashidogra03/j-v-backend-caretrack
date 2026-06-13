package com.caretrack.provider.dto;

import com.caretrack.provider.model.ProviderType;

import java.math.BigDecimal;

/**
 * Response object for nearby provider search including computed distance.
 */
public class NearbyProviderResponse {
    private Long id;
    private String name;
    private ProviderType type;
    private String city;
    private String state;
    private String country;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String phone;
    private String email;
    private double distanceKm;

    public NearbyProviderResponse() {}

    public NearbyProviderResponse(Long id, String name, ProviderType type, String city, String state, String country,
                                  BigDecimal latitude, BigDecimal longitude, String phone, String email, double distanceKm) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.city = city;
        this.state = state;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.email = email;
        this.distanceKm = distanceKm;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public ProviderType getType() { return type; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getCountry() { return country; }
    public BigDecimal getLatitude() { return latitude; }
    public BigDecimal getLongitude() { return longitude; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public double getDistanceKm() { return distanceKm; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(ProviderType type) { this.type = type; }
    public void setCity(String city) { this.city = city; }
    public void setState(String state) { this.state = state; }
    public void setCountry(String country) { this.country = country; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }
}
