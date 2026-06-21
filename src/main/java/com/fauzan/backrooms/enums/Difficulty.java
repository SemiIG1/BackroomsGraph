package com.fauzan.backrooms.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Difficulty {
    ZERO(0, "#F7E375"),
    ONE(1, "#FFC90E"),
    TWO(2, "#F59C00"),
    THREE(3, "#F95A00"),
    FOUR(4, "#FE1701"),
    FIVE(5, "#AF0606"),
    UNKNOWN(6, "#262626");
    private final int value;

    private final String hexColor;
    Difficulty(int value, String hexColor) {
        this.value = value;
        this.hexColor = hexColor;
    }

    public String getHexColor() {
        return hexColor;
    }

    public static Difficulty getValue(String difficulty) {
        String cleanInput = difficulty.trim().toLowerCase();
        Matcher matcher = Pattern.compile("\\D*([0-5]){1}([e?])?.*").matcher(cleanInput);
        if (matcher.find()) {
            int extractedNumber = Integer.parseInt(matcher.group(1));
            return Difficulty.values()[extractedNumber];
        }
        switch (cleanInput) {
            case "blanche", "habitable" -> {
                return ZERO;
            }
            case "paradise" -> {
                return FIVE;
            }
            case "amended", "n/a", "omega","pending", "unknown", "" -> {
                return UNKNOWN;
            }
        }
        return UNKNOWN;
    }
}
