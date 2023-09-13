package com.onrender.navkolodozvillya.location;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    @Mock
    private GeoIpService geoIpService;
    @Mock
    private HttpServletRequest request;
    private LocationController underTest;

    @BeforeEach
    void setUp() {
        underTest = new LocationController(geoIpService);
    }

    @Test
    public void shouldReturnLocation() {
        // given
        String ipAddress = "80.92.227.49";
        var location = new GeoIP(ipAddress, "Kyiv", 50.458, 30.5303);
        given(geoIpService.getIpLocation(any(HttpServletRequest.class)))
                .willReturn(location);
        // when
        var result = underTest.getCity(request);
        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(location);
    }

    @Test
    //TODO: Rewrite test using approach with mock requests
    public void getAllTest() {

        List<CityResponse> list = List.of(CityResponse.builder()
                        .geonameId(1)
                        .cityName("test1")
                        .uaCityName("тест1")
                        .build(),
                CityResponse.builder()
                        .geonameId(2)
                        .cityName("test2")
                        .uaCityName("тест2")
                        .build());

        List<CityResponse> expectedResponse = List.of(CityResponse.builder()
                        .geonameId(1)
                        .cityName("test1")
                        .uaCityName("тест1")
                        .build(),
                CityResponse.builder()
                        .geonameId(2)
                        .cityName("test2")
                        .uaCityName("тест2")
                        .build());

        given(geoIpService.getAllCities()).willReturn(list);
        // when
        var result = underTest.getAll();
        // then
        assertThat(result).isEqualTo(expectedResponse);
        verify(geoIpService, times(1)).getAllCities();
    }
}