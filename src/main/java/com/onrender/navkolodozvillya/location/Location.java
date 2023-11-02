package com.onrender.navkolodozvillya.location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "geoip2_location")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    @Id
    @Column(name = "geoname_id")
    private Integer id;

    private String cityName;
    private String uaCityName;
}
