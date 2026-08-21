package dev.mememarket.service;

import dev.mememarket.model.Company;

import java.util.Comparator;
import java.util.List;

public final class LeaderboardService {
    public List<Company> byMarketCap(List<Company> companies, int limit) {
        return companies.stream()
                .sorted(Comparator.comparingDouble(Company::marketCap).reversed())
                .limit(limit)
                .toList();
    }
}
