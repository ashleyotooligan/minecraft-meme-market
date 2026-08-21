package dev.mememarket.service;

import dev.mememarket.model.Company;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class DividendService {
    public Map<UUID, Double> calculatePayouts(Company company, double payoutRatio) {
        double pool = Math.max(0.0, company.treasury() * payoutRatio);
        var payouts = new HashMap<UUID, Double>();
        if (pool <= 0 || company.totalShares() <= 0) return payouts;

        for (var entry : company.holdings().entrySet()) {
            double ownership = (double) entry.getValue() / company.totalShares();
            payouts.put(entry.getKey(), pool * ownership);
        }
        return payouts;
    }
}
