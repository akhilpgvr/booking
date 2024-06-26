package com.managementidea.booking.model.repo;

import com.managementidea.booking.model.entities.BusBookingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusBookingRepo extends MongoRepository<BusBookingEntity, String> {

    Page<BusBookingEntity> findByMobileNo(String mobileNo, Pageable pageable);
}
