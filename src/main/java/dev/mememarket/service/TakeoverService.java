package dev.mememarket.service;

import dev.mememarket.model.Company;

import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;

public final class TakeoverService {
    private final double threshold;

    public TakeoverService(double threshold) {
        this.threshold = threshold;
    }

    public Optional<UUID> controllingShareholder(Company company) {
        return company.holdings().entrySet().stream()
                .filter(e -> (double) e.getValue() / company.totalShares() >= threshold)
                .max(Comparator.comparingInt(e -> e.getValue()))
                .map(e -> e.getKey());
    }
}
