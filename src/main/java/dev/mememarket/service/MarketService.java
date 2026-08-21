package dev.mememarket.service;

import dev.mememarket.model.Company;
import dev.mememarket.persistence.MarketRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class MarketService {
    private final MarketRepository repository;
    private final PriceEngine priceEngine;
    private final NewsService newsService;
    private final DividendService dividendService;
    private final TakeoverService takeoverService;
    private final Map<String, Company> companies = new ConcurrentHashMap<>();
    private final Map<String, Double> orderPressure = new ConcurrentHashMap<>();

    public MarketService(MarketRepository repository,
                         PriceEngine priceEngine,
                         NewsService newsService,
                         DividendService dividendService,
                         TakeoverService takeoverService) {
        this.repository = repository;
        this.priceEngine = priceEngine;
        this.newsService = newsService;
        this.dividendService = dividendService;
        this.takeoverService = takeoverService;
    }

    public void load() {
        companies.clear();
        for (Company company : repository.loadCompanies()) {
            companies.put(company.ticker(), company);
        }
    }

    public void save() {
        repository.saveCompanies(companies.values());
    }

    public void tick() {
        for (Company company : companies.values()) {
            double pressure = orderPressure.getOrDefault(company.ticker(), 0.0);
            company.sharePrice(priceEngine.nextPrice(company, pressure));
            newsService.decay(company);
            orderPressure.merge(company.ticker(), 0.5, (oldValue, multiplier) -> oldValue * multiplier);
            takeoverService.controllingShareholder(company).ifPresent(controller -> {
                if (!controller.equals(company.ceo())) company.ceo(controller);
            });
        }
        save();
    }

    public Company createCompany(String name, String ticker, UUID ceo, double price, int shares) {
        String normalized = ticker.toUpperCase(Locale.ROOT);
        if (companies.containsKey(normalized)) throw new IllegalArgumentException("Ticker already exists");
        var company = new Company(name, normalized, ceo, price, shares);
        companies.put(normalized, company);
        save();
        return company;
    }

    public Optional<Company> company(String ticker) {
        return Optional.ofNullable(companies.get(ticker.toUpperCase(Locale.ROOT)));
    }

    public List<Company> companies() {
        return companies.values().stream().sorted(Comparator.comparing(Company::ticker)).toList();
    }

    public double buy(UUID playerId, String ticker, int shares) {
        Company c = company(ticker).orElseThrow(() -> new IllegalArgumentException("Unknown ticker"));
        if (shares <= 0) throw new IllegalArgumentException("Shares must be positive");
        c.holdings().merge(playerId, shares, Integer::sum);
        orderPressure.merge(c.ticker(), shares / 100.0, Double::sum);
        return c.sharePrice() * shares;
    }

    public double sell(UUID playerId, String ticker, int shares) {
        Company c = company(ticker).orElseThrow(() -> new IllegalArgumentException("Unknown ticker"));
        int owned = c.sharesOwned(playerId);
        if (shares <= 0 || shares > owned) throw new IllegalArgumentException("Not enough shares");
        c.holdings().put(playerId, owned - shares);
        orderPressure.merge(c.ticker(), -shares / 100.0, Double::sum);
        return c.sharePrice() * shares;
    }

    public NewsService news() { return newsService; }
    public DividendService dividends() { return dividendService; }
}
