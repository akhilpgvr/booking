package com.managementidea.booking.service.client;

import com.managementidea.booking.model.dtos.request.BookJourneyRequest;
import com.managementidea.booking.model.dtos.response.BusRoutesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "BusManagementClient", url = "${config.rest.service.bus-url}")
public interface BusClient {

    @GetMapping("/internal/get-buses")
    ResponseEntity<List<BusRoutesResponse>> getBusesOnRoute(@RequestParam String origin
            , @RequestParam String destination, @RequestParam String departureDate);

    @PostMapping("/internal/book-ticket")
    ResponseEntity<String> bookTicket(@RequestBody BookJourneyRequest request);
}
