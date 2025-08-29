package com.codewithahmed.ecommerce.address;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto toAddressDto(Address address);
    Address toAddress(AddressDto addressDto);
}
