package com.caretrack.provider.service;

import com.caretrack.provider.dto.NearbyProviderResponse;
import com.caretrack.provider.dto.ProviderCreateRequest;
import com.caretrack.provider.dto.ProviderResponse;
import com.caretrack.provider.model.Provider;
import com.caretrack.provider.model.ProviderType;
import com.caretrack.provider.repo.ProviderRepository;
import jakarta.transaction.Transactional;//transcation occurs as whole otherwise rollback
import org.apache.commons.lang3.ObjectUtils;//This is an external open-source library, not part of Java.It contains many helper functions Java does not have.
                                           // eg::defaultIfNull(value, defaultValue)
import org.springframework.stereotype.Service;//Service:Marks this class as a Spring service bean so it can be injected into controllers or other components.

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;//JDK Built-in Class)
import java.util.stream.Collectors;

/**
 * ProviderService encapsulates provider creation and nearby search.
 * Nearby search uses the Haversine formula for distance calculation.
 */
@Service
public class ProviderService {

    private static final double EARTH_RADIUS_KM = 6371.0;//Constant value: Earth radius in kilometers → required for Haversine distance formula.

    private final ProviderRepository providerRepository;//A JPA repository interface used to interact with the database table for providers.

    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }//Sring automatically injects the providerRepository bean.

    @Transactional//If anything fails during save, entire operation rolls back.
    public ProviderResponse createProvider(ProviderCreateRequest req) {
        Provider p = new Provider();//create an entity class
        p.setName(req.getName());
        p.setType(ObjectUtils.defaultIfNull(req.getType(), ProviderType.HOSPITAL));//Meaning:If req.getType() is null → use ProviderType.HOSPITAL .If NOT null → use actual value
        p.setAddressLine1(req.getAddressLine1());
        p.setAddressLine2(req.getAddressLine2());
        p.setCity(req.getCity());
        p.setState(req.getState());
        p.setCountry(req.getCountry());
        p.setPostalCode(req.getPostalCode());
        p.setLatitude(req.getLatitude());//GPS coordinates
        p.setLongitude(req.getLongitude());
        p.setPhone(req.getPhone());
        p.setEmail(req.getEmail());
        p.setActive(true);//Sets active provider.

        Provider saved = providerRepository.save(p);
        return toResponse(saved);//Convert entity → response DTO
    }

    /** finding nearby providers using GPS coordinates.

     * Finds nearby providers around a given lat/lon within the specified radiusKm.
     * If type is provided, restricts to that provider type.
     */
    @Transactional//these are validations logic for fetching nearby providers..they are mandatory//this method helps in Fetches nearby providers using the Haversine distance.
    public List<NearbyProviderResponse> findNearbyProviders(BigDecimal lat, BigDecimal lon, Double radiusKm, ProviderType type) {
        if (lat == null || lon == null) {//Latitude and longitude are mandatory.coz it helps in finding providers nearby to the customers//Remove invalid data
            throw new IllegalArgumentException("lat and lon are required");
        }
        double radius = radiusKm != null ? radiusKm : 10.0;//set default radius:If radius is not provided → default = 10 km.

        List<Provider> base = (type == null)//fetch providers: If no type → fetch all active providers. If type given → fetch active providers of that type only.
                ? providerRepository.findAllByActiveTrue()
                : providerRepository.findAllByActiveTrueAndType(type);

        double lat0 = lat.doubleValue();//Convert center point from BigDecimal → double
        double lon0 = lon.doubleValue();//these are user/customer coordinates in degree
 //here stream processing begins

        return base.stream()
                .filter(p -> p.getLatitude() != null && p.getLongitude() != null)  //Skip providers lacking GPS coordinates.here p is provider
                .map(p -> {
                    double dist = haversineKm(lat0, lon0, p.getLatitude().doubleValue(), p.getLongitude().doubleValue());

                    //here above we Calls haversineKm() to calculate distance in double datatype
                    return new NearbyProviderResponse(
                            p.getId(),
                            p.getName(),
                            p.getType(),
                            p.getCity(),
                            p.getState(),
                            p.getCountry(),
                            p.getLatitude(),
                            p.getLongitude(),
                            p.getPhone(),
                            p.getEmail(),
                            dist
                    );
                })
                .filter(r -> r.getDistanceKm() <= radius)//help in finding out provider within this radius ...Only nearby providers remain.//Because the
                // sorting criteria (distance) does NOT exist in the Provider entity.
               //The distance is calculated ONLY inside the DTO, not stored in the database.
                // So:Provider entity has NO distance field ...NearbyProviderResponse DTO HAS distanceKm .Therefore sorting must be done on the DTO.
                .sorted(Comparator.comparingDouble(NearbyProviderResponse::getDistanceKm))
                                           //Sort by shortest distance....Near → far.
                                          // Because NearbyProviderResponse does not have one natural sorting order and using Comparable would force
                                          // all sorting to follow only one rule (distance),
                                          //which makes the DTO inflexible and mixes business logic into a data class.coz dto contains multiple fields
                                         //and Which of these should define the default sorting?hence need customized sorting
                                          //in future we also get NearByProviderResponse by getName,getCity,getState,Ratings
                                          // Therefor:Comparator is the correct approach

                .collect(Collectors.toList());//Collect final list
    }

    private ProviderResponse toResponse(Provider p) {
        Objects.requireNonNull(p, "provider");//It contains utility methods such as:requireNonNull(obj, message) .Ensures the object is NOT null.
                                              //If Entity p is null → throw NullPointerException("provider") .If p is NOT null → continue
                                              //Used for validation
        return new ProviderResponse(//conversion to dto
                p.getId(),
                p.getName(),
                p.getType(),
                p.getAddressLine1(),
                p.getAddressLine2(),
                p.getCity(),
                p.getState(),
                p.getCountry(),
                p.getPostalCode(),
                p.getLatitude(),
                p.getLongitude(),
                p.getPhone(),
                p.getEmail(),
                p.isActive()
        );
    }


    /**
     * Haversine distance in kilometers between two lat/lon points.The Haversine formula is
     * used to calculate the shortest distance between two points on the Earth (i.e., great-circle distance) using their latitude and longitude.
     */
    //Ideal for:GPS applications,Google Maps–style distance calculation ,Tracking and  geo-location systems
    //These represent the user's location, i.e., the center point:
    //lat0 → user latitude,,,,lon0 → user longitude:::which is passed as argument int haversine formula
    //These are the coordinates from which we want to find nearby providers.

    private double haversineKm(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1); //Calculates great-circle distance between two GPS points.and converting into radians
        double dLon = Math.toRadians(lon2 - lon1);
            double rLat1 = Math.toRadians(lat1);// Convert coordinates to radians
            double rLat2 = Math.toRadians(lat2);
//haversine formula
        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(rLat1) * Math.cos(rLat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return EARTH_RADIUS_KM * c; //Distance = Earth radius * angle
    }
}
//Earth coordinates are in degrees, but trigonometric functions require radians.Haversine formula mathematically requires radians
// giving haversine formula
//Given two points:
//
//Latitudes: φ₁, φ₂
//
//Longitudes: λ₁, λ₂
//
//Earth radius R (mean radius = 6371 km or 3958.8 miles)
//
//Formula
//a = sin²((φ₂ − φ₁)/2) + cos(φ₁) * cos(φ₂) * sin²((λ₂ − λ₁)/2)
//
//c = 2 * atan2(√a, √(1 − a))
//
//distance = R * c
//. Why convert BigDecimal → double?::::Reason:DB stores lat/lon as BigDecimal (good for precision)
//Haversine formula requires double for math functions (sin, cos, sqrt)...So conversion is required because:
//✔ Math.toRadians()
//✔ Math.sin()
//✔ Math.cos()
//✔ Math.sqrt()...All of these only accept double, not BigDecimal.
//usage of ?::::it is Ternary Operator = short form of if/else
//Structure:(condition) ? (value if true) : (value if false)
//
//
//
//
//
//
//
//
//
//