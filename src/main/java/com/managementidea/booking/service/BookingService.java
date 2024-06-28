package com.managementidea.booking.service;

import com.managementidea.booking.model.dtos.request.BookJourneyRequest;
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class BookingService {

    @Autowired
    private BusClient busClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private BusBookingRepo busBookingRepo;

    public String bookJourney(BookJourneyRequest request) {

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
        busBookingRepo.save(booking);
        return "OK";
    }

    public List<BusRoutesResponse> getBusesOnRoute(String origin, String destination, String departureDate) {

        return busClient.getBusesOnRoute(origin, destination, departureDate).getBody();
    }
}
