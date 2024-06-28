package com.managementidea.booking.model.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusRoutesResponse {

    private String origin;
    private String destination;
    private List<String> stops;   //<stops>
    private HashMap<String, String> fares;  //<destination, fare>
    private String departureDate;
    private String arrivalDate;
    private int availableSeats;
    private EmployeeDetails employeeDetails;
}
