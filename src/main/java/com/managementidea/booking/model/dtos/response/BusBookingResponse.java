package com.managementidea.booking.model.dtos.response;

import com.managementidea.booking.model.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusBookingResponse {

    private String passengerName;
    private String age;
    private GenderEnum genderEnum;
    private String mobileNo;
    private String busRegNo;
    private String origin;
    private String destination;
    private String departureDate;
    private int noOfSeats;
    private String charge;
}
