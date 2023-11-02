package com.onrender.navkolodozvillya.location;

import com.onrender.navkolodozvillya.offering.Offering;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Override
    List<Location> findAll();
}
