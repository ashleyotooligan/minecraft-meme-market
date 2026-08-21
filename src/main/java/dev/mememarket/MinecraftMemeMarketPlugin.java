package dev.mememarket;

import dev.mememarket.command.CompanyCommand;
import dev.mememarket.command.MarketCommand;
import dev.mememarket.command.TradeCommand;
import dev.mememarket.listener.PlayerJoinListener;
import dev.mememarket.persistence.YamlMarketRepository;
import dev.mememarket.service.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftMemeMarketPlugin extends JavaPlugin {
    private MarketService marketService;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        var repository = new YamlMarketRepository(this);
        var priceEngine = new PriceEngine(
                getConfig().getDouble("market.base-volatility", 0.035),
                getConfig().getDouble("market.max-price-change-per-tick", 0.20),
                getConfig().getDouble("market.minimum-price", 0.01));

        var newsService = new NewsService(getConfig().getDouble("news.decay-per-tick", 0.20));
        var dividendService = new DividendService();
        var takeoverService = new TakeoverService(getConfig().getDouble("takeovers.control-threshold", 0.51));

        this.marketService = new MarketService(repository, priceEngine, newsService, dividendService, takeoverService);
        this.marketService.load();

        registerCommands();
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(marketService), this);
        startMarketTicker();
        getLogger().info("Minecraft Meme Market enabled with " + marketService.companies().size() + " companies.");
    }

    @Override
    public void onDisable() {
        if (marketService != null) {
            marketService.save();
        }
    }

    private void registerCommands() {
        if (getCommand("market") != null) getCommand("market").setExecutor(new MarketCommand(marketService));
        if (getCommand("company") != null) getCommand("company").setExecutor(new CompanyCommand(marketService));
        if (getCommand("trade") != null) getCommand("trade").setExecutor(new TradeCommand(marketService));
    }

    private void startMarketTicker() {
        long seconds = getConfig().getLong("market.tick-seconds", 30L);
        long ticks = Math.max(20L, seconds * 20L);
        getServer().getScheduler().runTaskTimer(this, marketService::tick, ticks, ticks);
    }
}
