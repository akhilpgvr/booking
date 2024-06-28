package com.managementidea.booking.model.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookJourneyRequest {

    @JsonIgnore
    private String mobileNo;
    private String busRegNo;
    private String origin;
    private String destination;
    private String departureDate;
    private int noOfSeats;
}
