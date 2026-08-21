package dev.mememarket.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Company {
    private final String name;
    private final String ticker;
    private UUID ceo;
    private double sharePrice;
    private int totalShares;
    private double treasury;
    private double revenueToday;
    private double newsImpact;
    private final Map<UUID, Integer> holdings = new HashMap<>();

    public Company(String name, String ticker, UUID ceo, double sharePrice, int totalShares) {
        this.name = name;
        this.ticker = ticker.toUpperCase();
        this.ceo = ceo;
        this.sharePrice = sharePrice;
        this.totalShares = totalShares;
    }

    public String name() { return name; }
    public String ticker() { return ticker; }
    public UUID ceo() { return ceo; }
    public void ceo(UUID ceo) { this.ceo = ceo; }
    public double sharePrice() { return sharePrice; }
    public void sharePrice(double value) { this.sharePrice = value; }
    public int totalShares() { return totalShares; }
    public double treasury() { return treasury; }
    public void treasury(double value) { this.treasury = value; }
    public double revenueToday() { return revenueToday; }
    public void revenueToday(double value) { this.revenueToday = value; }
    public double newsImpact() { return newsImpact; }
    public void newsImpact(double value) { this.newsImpact = value; }
    public Map<UUID, Integer> holdings() { return holdings; }

    public int sharesOwned(UUID player) { return holdings.getOrDefault(player, 0); }
    public double marketCap() { return sharePrice * totalShares; }
    public double ownership(UUID player) { return totalShares == 0 ? 0 : (double) sharesOwned(player) / totalShares; }
}
