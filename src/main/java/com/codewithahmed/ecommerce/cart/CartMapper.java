package com.codewithahmed.ecommerce.cart;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    Cart toCart(CartDto cartDto);

    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toCartDto(Cart cart);

    @Mapping(target = "product", source = "product")
    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toCartItemDto(CartItem cartItem);
}
