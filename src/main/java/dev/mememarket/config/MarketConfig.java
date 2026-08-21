package dev.mememarket.config;

public record MarketConfig(
        int tickSeconds,
        double baseVolatility,
        double maxPriceMove,
        double minimumPrice,
        double takeoverThreshold
) { }
