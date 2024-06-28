package com.managementidea.booking.model.dtos.response;

import com.managementidea.booking.model.enums.GenderEnum;
import com.managementidea.booking.model.enums.PrefCommunicaionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonnelInfo {

    private String firstName;
    private String lastName;
    private String age;
    private GenderEnum gender;
    private String mobCountryCode;
    private String mobileNo;
    private String email;
    private PrefCommunicaionEnum prefCommunication;
}
