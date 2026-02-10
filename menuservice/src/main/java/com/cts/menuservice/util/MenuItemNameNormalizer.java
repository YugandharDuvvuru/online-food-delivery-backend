package com.cts.menuservice.util;

import org.springframework.stereotype.Component;

@Component
public final class MenuItemNameNormalizer {
    private MenuItemNameNormalizer() {}

    /**
     * Normalize a menu item name for uniqueness checks:
     * - lowercase
     * - trim leading/trailing spaces
     * - remove all non-alphanumeric characters (spaces, hyphens, punctuation)
     * Returns empty string if name is null or blank.
     */
    public static String normalize(String name) {
        if (name == null) return "";
        String trimmed = name.trim().toLowerCase();
        if (trimmed.isBlank()) return "";
        // keep only a-z 0-9
        return trimmed.replaceAll("[^a-z0-9]", "");
    }
}
