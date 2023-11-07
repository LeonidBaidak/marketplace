package com.onrender.navkolodozvillya.offering;

import com.onrender.navkolodozvillya.exception.entity.offering.OfferingNotFoundException;
import com.onrender.navkolodozvillya.location.Location;
import com.onrender.navkolodozvillya.location.LocationRepository;
import com.onrender.navkolodozvillya.media.MediaMetadata;
import com.onrender.navkolodozvillya.media.MediaMetadataRepository;
import com.onrender.navkolodozvillya.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferingService {
    private final OfferingRepository offeringRepository;
    private final OfferingResponseMapper offeringResponseMapper;
    private final ConversionService conversionService;
    private final LocationRepository locationRepository;
    private final MediaMetadataRepository mediaMetadataRepository;

    public List<OfferingResponse> findAll(Pageable pageable) {
        return offeringRepository.findBy(pageable)
                .map(offeringResponseMapper::offeringToOfferingResponseDto)
                .toList();
    }

    public OfferingResponse findById(Long offeringId) {
        return offeringRepository.findById(offeringId)
                .map(offeringResponseMapper::offeringToOfferingResponseDto)
                .orElseThrow(() -> new OfferingNotFoundException("Offering not found with id - " + offeringId));
    }

    public OfferingCreateResponse create(OfferingCreateRequest request, User user) {
        Offering offeringToCreate = conversionService.convert(request, Offering.class);
        Location location = locationRepository.findById(request.locationId()).orElseThrow();
        List<MediaMetadata> mediaMetadataList = mediaMetadataRepository.findAllById(request.mediaMetadataList());
        offeringToCreate.setLocation(location);
        offeringToCreate.setLinkedMediaMetadata(mediaMetadataList);
        offeringToCreate.setUser(user);
        Offering createdOffering = offeringRepository.save(offeringToCreate);
        return conversionService.convert(createdOffering, OfferingCreateResponse.class);
    }
}
