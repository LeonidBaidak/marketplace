package com.onrender.navkolodozvillya.location;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Tag(name = "Location Controller", description = "Location related APIs")
@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
@CrossOrigin //todo: expose to appropriate domains
public class LocationController {

    private final GeoIpService geoIpService;

    @Operation(summary = "Get city location",
            description = "Retrieve the location information for the user's city based on their IP address")
    @GetMapping
    public ResponseEntity<GeoIP> getCity(HttpServletRequest request){
        var location = geoIpService.getIpLocation(request);
        return ResponseEntity.ok(location);
    }

    @Operation(summary = "Get all cities",
            description = "Retrive summary information about all cities: id, English name, optional Ukrainian name")
    @GetMapping("/getAll")
    public List<CityResponse> getAll(){
        return geoIpService.getAllCities();
    }
}
