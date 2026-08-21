package dev.mememarket.model;

import java.time.Instant;

public record NewsEvent(String ticker, String headline, double impact, Instant createdAt) { }
