package com.managementidea.booking.model.entities;

import com.managementidea.booking.model.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bus-booking")
public class BusBookingEntity {

    @Id
    @Generated
    private String id;

    private String passengerName;
    private String age;
    private GenderEnum genderEnum;
    private String mobileNo;
    private String busRegNo;
    private String origin;
    private String destination;
    private String departureDate;
    private String noOfSeats;
    private String charge;
    private LocalDateTime createdOn;
    private LocalDateTime lastUpdatedOn;
}
