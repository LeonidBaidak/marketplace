package com.example.marketplace;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import ua_parser.Client;
import ua_parser.Parser;

import java.io.IOException;
import java.net.InetAddress;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class GeoIPLocationService {

    private final DatabaseReader databaseReader;
    private static final String UNKNOWN = "UNKNOWN";

    private String getDeviceDetails(String userAgent) throws IOException {
        String deviceDetails = UNKNOWN;

        Parser parser = new Parser();

        Client client = parser.parse(userAgent);
        if (nonNull(client)) {
            deviceDetails = client.userAgent.family + " " + client.userAgent.major + "." + client.userAgent.minor +
                    " - " + client.os.family + " " + client.os.major + "." + client.os.minor;
        }

        return deviceDetails;
    }

    public GeoIP getIpLocation(String ip, HttpServletRequest request) throws IOException, GeoIp2Exception {

        GeoIP position = new GeoIP();
        String location;

        InetAddress ipAddress = InetAddress.getByName(ip);

        CityResponse cityResponse = databaseReader.city(ipAddress);
        if (nonNull(cityResponse) && nonNull(cityResponse.getCity())) {

            String continent = (cityResponse.getContinent() != null) ? cityResponse.getContinent().getName() : "";
            String country = (cityResponse.getCountry() != null) ? cityResponse.getCountry().getName() : "";

            location = String.format("%s, %s, %s", continent, country, cityResponse.getCity().getName());
            position.setCity(cityResponse.getCity().getName());
            position.setFullLocation(location);
            position.setLatitude((cityResponse.getLocation() != null) ? cityResponse.getLocation().getLatitude() : 0);
            position.setLongitude((cityResponse.getLocation() != null) ? cityResponse.getLocation().getLongitude() : 0);
            position.setDevice(getDeviceDetails(request.getHeader("user-agent")));
            position.setIpAddress(ip);

        }
        return position;
    }
}
