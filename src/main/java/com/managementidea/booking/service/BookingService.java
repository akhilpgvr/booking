package com.managementidea.booking.service;

import com.managementidea.booking.model.dtos.request.BookJourneyRequest;
import com.managementidea.booking.model.dtos.response.BusBookingResponse;
import com.managementidea.booking.model.dtos.response.BusRoutesResponse;
import com.managementidea.booking.model.dtos.response.FindByMobileResponse;
import com.managementidea.booking.model.dtos.response.PersonnelInfo;
import com.managementidea.booking.model.entities.BusBookingEntity;
import com.managementidea.booking.model.enums.UserTypeEnum;
import com.managementidea.booking.model.repo.BusBookingRepo;
import com.managementidea.booking.service.client.BusClient;
import com.managementidea.booking.service.client.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BookingService {

    @Autowired
    private BusClient busClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private BusBookingRepo busBookingRepo;
    @Autowired
    private MongoTemplate mongoTemplate;

    public BusBookingResponse bookJourney(BookJourneyRequest request) {

        log.info("checking for duplicate bookings");
        BusBookingResponse busBookingResponse = new BusBookingResponse();
        BusBookingEntity bookingRef = checkForDuplicateBooking(request);

        if(Objects.isNull(bookingRef)) {
            log.info("Getting user details for: {}", request.getMobileNo());
            FindByMobileResponse user = userClient.findByMobileNoAndUserType(request.getMobileNo(), UserTypeEnum.PASSENGER).getBody();
            String fare = busClient.bookTicket(request).getBody();
            BusBookingEntity booking = new BusBookingEntity();
            BeanUtils.copyProperties(request, booking);
            PersonnelInfo userInfo = user.getUserInfo();
            booking.setPassengerName(userInfo.getFirstName()+userInfo.getLastName());
            booking.setAge(userInfo.getAge());
            booking.setGenderEnum(userInfo.getGender());
            booking.setCharge(fare);
            booking.setCreatedOn(LocalDateTime.now());
            booking.setLastUpdatedOn(LocalDateTime.now());
            log.info("Saving booking for: {}", request.getBusRegNo());
            booking = busBookingRepo.save(booking);
            BeanUtils.copyProperties(booking, busBookingResponse);
        }
        else {

            log.info("Booking already exists for: {}", request.getBusRegNo());
            String fare = busClient.bookTicket(request).getBody();
            int seatsBooked = bookingRef.getNoOfSeats();
            bookingRef.setNoOfSeats(seatsBooked+ request.getNoOfSeats());
            bookingRef.setCharge(String.valueOf(Integer.parseInt(fare)+ Integer.parseInt(bookingRef.getCharge())));
            log.info("Saving booking for: {}", request.getBusRegNo());
            bookingRef = busBookingRepo.save(bookingRef);
            BeanUtils.copyProperties(bookingRef, busBookingResponse);
        }

        return busBookingResponse;
    }

    private BusBookingEntity checkForDuplicateBooking(BookJourneyRequest request) {

        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("busRegNo").is(request.getBusRegNo()),
                Criteria.where("origin").is(request.getOrigin()),
                Criteria.where("destination").is(request.getDestination()),
                Criteria.where("departureDate").is(request.getDepartureDate())
        ));
        return mongoTemplate.findOne(query, BusBookingEntity.class);
    }

    public List<BusRoutesResponse> getBusesOnRoute(String origin, String destination, String departureDate) {

        return busClient.getBusesOnRoute(origin, destination, departureDate).getBody();
    }
}
