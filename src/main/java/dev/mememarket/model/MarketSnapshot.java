package dev.mememarket.model;

public record MarketSnapshot(String ticker, double price, double marketCap, double treasury, double revenueToday, double newsImpact) { }
