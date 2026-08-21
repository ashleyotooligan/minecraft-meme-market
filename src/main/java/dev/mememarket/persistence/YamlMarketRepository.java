package dev.mememarket.persistence;

import dev.mememarket.model.Company;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class YamlMarketRepository implements MarketRepository {
    private final JavaPlugin plugin;
    private final File file;

    public YamlMarketRepository(JavaPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "companies.yml");
    }

    @Override
    public List<Company> loadCompanies() {
        if (!file.exists()) return new ArrayList<>();
        var yaml = YamlConfiguration.loadConfiguration(file);
        var section = yaml.getConfigurationSection("companies");
        if (section == null) return new ArrayList<>();

        var result = new ArrayList<Company>();
        for (String ticker : section.getKeys(false)) {
            String path = "companies." + ticker + ".";
            String name = yaml.getString(path + "name", ticker);
            UUID ceo = UUID.fromString(Objects.requireNonNull(yaml.getString(path + "ceo")));
            double price = yaml.getDouble(path + "price", 10.0);
            int shares = yaml.getInt(path + "total-shares", 100);
            var company = new Company(name, ticker, ceo, price, shares);
            company.treasury(yaml.getDouble(path + "treasury", 0.0));
            company.revenueToday(yaml.getDouble(path + "revenue-today", 0.0));
            var holdings = yaml.getConfigurationSection(path + "holdings");
            if (holdings != null) {
                for (String player : holdings.getKeys(false)) {
                    company.holdings().put(UUID.fromString(player), holdings.getInt(player));
                }
            }
            result.add(company);
        }
        return result;
    }

    @Override
    public void saveCompanies(Collection<Company> companies) {
        var yaml = new YamlConfiguration();
        for (Company c : companies) {
            String path = "companies." + c.ticker() + ".";
            yaml.set(path + "name", c.name());
            yaml.set(path + "ceo", c.ceo().toString());
            yaml.set(path + "price", c.sharePrice());
            yaml.set(path + "total-shares", c.totalShares());
            yaml.set(path + "treasury", c.treasury());
            yaml.set(path + "revenue-today", c.revenueToday());
            for (var entry : c.holdings().entrySet()) {
                yaml.set(path + "holdings." + entry.getKey(), entry.getValue());
            }
        }
        try {
            if (!plugin.getDataFolder().exists() && !plugin.getDataFolder().mkdirs()) {
                plugin.getLogger().warning("Could not create plugin data folder");
            }
            yaml.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save market data: " + e.getMessage());
        }
    }
}
