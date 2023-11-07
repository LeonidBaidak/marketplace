package com.onrender.navkolodozvillya.offering;

import com.onrender.navkolodozvillya.exception.entity.offering.OfferingNotFoundException;
import com.onrender.navkolodozvillya.location.LocationRepository;
import com.onrender.navkolodozvillya.media.MediaMetadataRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OfferingServiceTest {

    @Mock
    private OfferingRepository offeringRepository;
    @Mock
    private OfferingResponseMapper offeringResponseMapper;
    @Mock
    private ConversionService conversionService;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private MediaMetadataRepository mediaMetadataRepository;

    private OfferingService underTest;

    @BeforeEach
    void setUp() {
        underTest = new OfferingService(offeringRepository, offeringResponseMapper, conversionService,
                locationRepository, mediaMetadataRepository);
    }

    @Test
    public void shouldFindAllOfferings() {
        // given
        var offerings = getOfferingList();
        Slice<Offering> offeringPage = new PageImpl<>(offerings);
        given(offeringRepository.findBy(any(Pageable.class)))
                .willReturn(offeringPage);
        given(offeringResponseMapper.offeringToOfferingResponseDto(any(Offering.class)))
                .willAnswer(getOfferingResponseAnswer());
        // when
        var result = underTest.findAll(Pageable.unpaged());
        // then
        verify(offeringRepository, times(1)).findBy(any(Pageable.class));
        verify(offeringResponseMapper, times(2)).offeringToOfferingResponseDto(any(Offering.class));
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void shouldFindOfferingById() {
        // given
        var offeringId = 2L;
        var offering = getOfferingList().get(1);
        given(offeringRepository.findById(offeringId))
                .willReturn(Optional.of(offering));
        given(offeringResponseMapper.offeringToOfferingResponseDto(any(Offering.class)))
                .willAnswer(getOfferingResponseAnswer());
        // when
        var result = underTest.findById(offeringId);
        // then
        verify(offeringRepository, times(1)).findById(offeringId);
        verify(offeringResponseMapper, times(1)).offeringToOfferingResponseDto(offering);
        assertThat(result.description()).isEqualTo(offering.getDescription());
    }

    @Test
    public void shouldThrowWhenCanNotFindById() {
        // given
        var offeringId = 900L;
        given(offeringRepository.findById(anyLong()))
                .willReturn(Optional.empty());
        // then
        assertThatThrownBy(() -> underTest.findById(offeringId))
                .isInstanceOf(OfferingNotFoundException.class)
                .hasMessage("Offering not found with id - " + offeringId);
    }

    @NotNull
    private static Answer<Object> getOfferingResponseAnswer() {
        return invocation -> {
            Offering offering = invocation.getArgument(0);
            return new OfferingResponse(
                    offering.getId(),
                    offering.getName(),
                    offering.getDescription(),
                    offering.getPrice(),
                    offering.getCategory());
        };
    }

    private static List<Offering> getOfferingList() {
        var summerMusicFestival = Offering.builder()
                .id(1L)
                .name("Summer Music Festival")
                .description("""
                        Join us for a fun-filled summer music festival
                        with live performances from top artists.
                        """)
                .price(BigDecimal.valueOf(100))
                .category(OfferingCategory.EVENT)
                .build();

        var charityGalaDinner = Offering.builder()
                .id(2L)
                .name("Charity Gala Dinner")
                .description("""
                        Support a good cause and enjoy an elegant evening
                        with a charity gala dinner.
                        """)
                .price(BigDecimal.valueOf(28.75))
                .category(OfferingCategory.EVENT)
                .build();

        return List.of(summerMusicFestival, charityGalaDinner);
    }

}