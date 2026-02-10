package com.cts.ownerservice.util;

import org.springframework.stereotype.Component;

@Component
public class RestaurantNameNormalizer {
    public RestaurantNameNormalizer(){

    }
    public static String normalize(String name) {
        if (name == null) return "";
        String trimmed = name.trim().toLowerCase();
        if (trimmed.isBlank()) return "";
        // keep only a-z 0-9
        return trimmed.replaceAll("[^a-z0-9]", "");
    }
}
