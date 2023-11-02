package com.onrender.navkolodozvillya.location;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
@Log4j2
public class GeoIpService {
    private final GeoIpRepository geoIpRepository;
    private final LocationRepository locationRepository;

    public GeoIP getIpLocation(HttpServletRequest request) {
        requireNonNull(request, "request is null");

        String ipAddress = getIpAddress(request);
        log.info("IP address received: {}", ipAddress);

        return geoIpRepository.getIpLocation(ipAddress)
                .orElse(new GeoIP(("Genereted for : " + ipAddress), "Kyiv", 50.4501, 30.5234));
    }

    //TODO temporary for compliance. Need to remove all JDBC repo logic, move it here and to SpringData repo
    public List<CityResponse> getAllCities() {
        return locationRepository.findAll().stream().map(location -> {
                    return CityResponse
                            .builder()
                            .geonameId(location.getId())
                            .cityName(location.getCityName())
                            .uaCityName(location.getUaCityName())
                            .build();
                })
                .collect(Collectors.toList());

    }

    private static String getIpAddress(HttpServletRequest request) {
        var ipAddress = request.getHeader("X-FORWARDED-FOR");

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = "0:0:0:0:0:0:0:1";
        }
        return ipAddress;
    }
}
