package dev.mememarket.service;

import dev.mememarket.model.Company;

import java.util.concurrent.ThreadLocalRandom;

public final class PriceEngine {
    private final double baseVolatility;
    private final double maxMove;
    private final double minimumPrice;

    public PriceEngine(double baseVolatility, double maxMove, double minimumPrice) {
        this.baseVolatility = baseVolatility;
        this.maxMove = maxMove;
        this.minimumPrice = minimumPrice;
    }

    public double nextPrice(Company company, double netOrderPressure) {
        double noise = ThreadLocalRandom.current().nextGaussian() * baseVolatility;
        double revenueSignal = Math.tanh(company.revenueToday() / Math.max(company.marketCap(), 1.0)) * 0.08;
        double orderSignal = Math.tanh(netOrderPressure) * 0.12;
        double rawMove = noise + revenueSignal + orderSignal + company.newsImpact();
        double boundedMove = Math.max(-maxMove, Math.min(maxMove, rawMove));
        return Math.max(minimumPrice, company.sharePrice() * (1.0 + boundedMove));
    }
}
