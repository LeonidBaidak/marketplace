package com.onrender.navkolodozvillya.location;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeoIpServiceImplTest {
    @Mock
    private GeoIpRepository geoIpRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private HttpServletRequest request;

    private GeoIpService underTest;

    @BeforeEach
    void setUp() {
        underTest = new GeoIpService(geoIpRepository, locationRepository);
    }

    @Test
    void canGetIpLocationWhenIpExists() {
        // given
        String ipAddress = "80.92.227.49";
        var location = new GeoIP(ipAddress, "Kyiv", 50.458, 30.5303);
        given(request.getRemoteAddr())
                .willReturn(ipAddress);
        given(geoIpRepository.getIpLocation(anyString()))
                .willReturn(Optional.of(location));
        // when
        var result = underTest.getIpLocation(request);
        // then
        verify(geoIpRepository, times(1)).getIpLocation(ipAddress);
        verify(request, times(1)).getRemoteAddr();
        assertThat(result).isEqualTo(location);
    }

    @Test
    void canGetIpLocationWhenIpDoesNotExistWithDefaultRecord() {
        // given
        String ipAddress = "80.92.227.49";
        given(request.getRemoteAddr())
                .willReturn(ipAddress);
        given(geoIpRepository.getIpLocation(anyString()))
                .willReturn(Optional.empty());

        var expectedLocation = new GeoIP("Genereted for : 80.92.227.49",
                "Kyiv", 50.4501, 30.5234);
        // when
        var result = underTest.getIpLocation(request);
        // then
        verify(geoIpRepository, times(1)).getIpLocation(ipAddress);
        verify(request, times(1)).getRemoteAddr();
        assertThat(result).isEqualTo(expectedLocation);
    }

    @Test
    void canGetIpLocationWhenIpIsNullWithDefaultRecord() {
        // given
        given(geoIpRepository.getIpLocation(anyString()))
                .willReturn(Optional.empty());
        var expectedLocation = new GeoIP("Genereted for : 0:0:0:0:0:0:0:1",
                "Kyiv", 50.4501, 30.5234);
        // when
        var result = underTest.getIpLocation(request);
        // then
        verify(geoIpRepository, times(1)).getIpLocation("0:0:0:0:0:0:0:1");
        verify(request, times(1)).getRemoteAddr();
        assertThat(result).isEqualTo(expectedLocation);
    }

    @Test
    void getAllCitiesTest() {
        List<Location> list = List.of(Location.builder()
                        .id(1)
                        .cityName("test1")
                        .uaCityName("тест1")
                        .build(),
                Location.builder()
                        .id(2)
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

        // given
        given(locationRepository.findAll()).willReturn(list);
        // when
        var actualResponse = underTest.getAllCities();
        // then
        assertThat(expectedResponse).isEqualTo(actualResponse);
        verify(locationRepository, times(1)).findAll();
        verifyNoMoreInteractions(geoIpRepository, locationRepository, request);
    }
}