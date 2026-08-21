package dev.mememarket.service;

import dev.mememarket.model.Company;
import dev.mememarket.model.NewsEvent;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public final class NewsService {
    private final double decayPerTick;
    private final List<NewsEvent> events = new ArrayList<>();

    public NewsService(double decayPerTick) {
        this.decayPerTick = Math.max(0.0, Math.min(1.0, decayPerTick));
    }

    public NewsEvent publish(Company company, String headline, double impact) {
        var event = new NewsEvent(company.ticker(), headline, impact, Instant.now());
        events.add(event);
        company.newsImpact(company.newsImpact() + impact);
        return event;
    }

    public void decay(Company company) {
        company.newsImpact(company.newsImpact() * (1.0 - decayPerTick));
        if (Math.abs(company.newsImpact()) < 0.0001) company.newsImpact(0.0);
    }

    public List<NewsEvent> events() {
        return List.copyOf(events);
    }
}
