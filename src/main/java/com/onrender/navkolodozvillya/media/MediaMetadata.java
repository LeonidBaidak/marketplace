package com.onrender.navkolodozvillya.media;

import com.onrender.navkolodozvillya.offering.Offering;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "media_metadata")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String objectKey;
    private Integer ordinal;

    @ManyToMany(mappedBy = "linkedMediaMetadata")
    @ToString.Exclude
    List<Offering> offerings;
}


