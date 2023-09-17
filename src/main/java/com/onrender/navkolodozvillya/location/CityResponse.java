package com.onrender.navkolodozvillya.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityResponse {
    
    int geonameId;
    String cityName;
    String uaCityName;
}
