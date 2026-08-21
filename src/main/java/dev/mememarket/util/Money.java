package dev.mememarket.util;

import java.text.DecimalFormat;

public final class Money {
    private static final DecimalFormat FORMAT = new DecimalFormat("#,##0.00");

    private Money() { }

    public static String format(double value) {
        return "$" + FORMAT.format(value);
    }
}
