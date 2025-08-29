package com.codewithahmed.ecommerce.address;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddressDto {
    private String street;
    private String city;
    private String country;
    private String zipCode;
}
