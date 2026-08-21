package dev.mememarket.ui;

import dev.mememarket.model.Company;

import java.util.List;

public final class MarketBoardRenderer {
    public List<String> renderCompany(Company company) {
        return List.of(
                "§6§l" + company.name().toUpperCase(),
                "§fTICKER: §e" + company.ticker(),
                "§fPRICE: §a$" + String.format("%.2f", company.sharePrice()),
                "§fMARKET CAP: §a$" + String.format("%.0f", company.marketCap()),
                "§fCASH: §a$" + String.format("%.0f", company.treasury()),
                "§fREVENUE TODAY: §a$" + String.format("%.0f", company.revenueToday())
        );
    }
}
