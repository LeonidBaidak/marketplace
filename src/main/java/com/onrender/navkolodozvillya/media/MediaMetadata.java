package com.onrender.navkolodozvillya.media;

import jakarta.persistence.*;
import lombok.*;


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
}


