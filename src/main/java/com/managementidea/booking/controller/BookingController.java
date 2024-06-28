package com.managementidea.booking.controller;

import com.managementidea.booking.model.dtos.request.BookJourneyRequest;
import com.managementidea.booking.model.dtos.response.BusBookingResponse;
import com.managementidea.booking.model.dtos.response.BusRoutesResponse;
import com.managementidea.booking.model.entities.BusBookingEntity;
import com.managementidea.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Operation(summary = "Api for booking Journey for user", description = "")
    @PostMapping("/journey")
    public ResponseEntity<BusBookingResponse> bookJourney(@RequestParam String mobileNo, @RequestBody BookJourneyRequest request) {

        request.setMobileNo(mobileNo);
        log.info("adding journey");
        return new ResponseEntity<>(bookingService.bookJourney(request), HttpStatus.OK);
    }

    @Operation(summary = "Api for filtering bus for user", description = "")
    @GetMapping("/get-buses")
    public ResponseEntity<List<BusRoutesResponse>> getBusesOnRoute(@RequestParam String origin
            , @RequestParam String destination, @RequestParam String departureDate) {

        log.info("fetching the info for origin: {}, destination: {}, departureDate: {}", origin, destination, departureDate);
        return new ResponseEntity<>(bookingService.getBusesOnRoute(origin, destination, departureDate), HttpStatus.OK);
    }

    @Operation(summary = "Api for getting booking history of user", description = "")
    @GetMapping("/history")
    public ResponseEntity<Page<BusBookingEntity>> getHistory(@RequestParam String mobileNo, @RequestParam int page, @RequestParam int size) {

        log.info("pagination to get the history");
        return new ResponseEntity<>(bookingService.getHistory(mobileNo, page, size), HttpStatus.OK);
    }
}
