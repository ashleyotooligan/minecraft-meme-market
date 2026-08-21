package dev.mememarket.model;

import java.time.Instant;
import java.util.UUID;

public record Trade(UUID playerId, String ticker, Side side, int shares, double price, Instant timestamp) {
    public enum Side { BUY, SELL }
}
