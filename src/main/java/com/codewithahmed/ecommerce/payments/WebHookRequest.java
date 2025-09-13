package com.codewithahmed.ecommerce.payments;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class WebHookRequest {
    private Map<String,String> headers;
    private String payload;
}
