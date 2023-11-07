package com.onrender.navkolodozvillya.offering;

import com.onrender.navkolodozvillya.favouriteoffering.FavouriteOffering;
import com.onrender.navkolodozvillya.location.Location;
import com.onrender.navkolodozvillya.media.MediaMetadata;
import com.onrender.navkolodozvillya.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "offerings")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"user", "favouriteOfferings"})
@AllArgsConstructor
@Builder
public class Offering {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OfferingCategory category;

    @Column(updatable = false)
    @Setter(AccessLevel.PRIVATE)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Setter(AccessLevel.PRIVATE)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "offering",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<FavouriteOffering> favouriteOfferings;

    @ManyToOne(fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(name = "location_id")
    private Location location;

    private LocalDateTime eventDateTime;

    @ManyToMany
    @JoinTable(
            name = "offerings_media_metadata",
            joinColumns = @JoinColumn(name = "offering_id"),
            inverseJoinColumns = @JoinColumn(name = "media_metadata_id"))
    private List<MediaMetadata> linkedMediaMetadata;

    public void addFavouriteOffering(FavouriteOffering favouriteOffering) {
        favouriteOfferings.add(favouriteOffering);
        favouriteOffering.setOffering(this);
    }

    public void removeFavouriteOffering(FavouriteOffering favouriteOffering) {
        favouriteOfferings.remove(favouriteOffering);
        favouriteOffering.setOffering(null);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Offering offering = (Offering) o;
        return getId() != null && Objects.equals(getId(), offering.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
