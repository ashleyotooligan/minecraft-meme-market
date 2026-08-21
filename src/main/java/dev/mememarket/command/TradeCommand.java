package dev.mememarket.command;

import dev.mememarket.service.MarketService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class TradeCommand implements CommandExecutor {
    private final MarketService market;

    public TradeCommand(MarketService market) { this.market = market; }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Players only.");
            return true;
        }

        if (args.length >= 3 && (args[0].equalsIgnoreCase("buy") || args[0].equalsIgnoreCase("sell"))) {
            try {
                int shares = Integer.parseInt(args[2]);
                boolean buy = args[0].equalsIgnoreCase("buy");
                double value = buy
                        ? market.buy(player.getUniqueId(), args[1], shares)
                        : market.sell(player.getUniqueId(), args[1], shares);
                sender.sendMessage("§a" + (buy ? "Bought " : "Sold ") + shares + " " + args[1].toUpperCase()
                        + " §7for §f$" + String.format("%.2f", value));
            } catch (Exception ex) {
                sender.sendMessage("§cTrade rejected: " + ex.getMessage());
            }
            return true;
        }

        if (args.length >= 1 && args[0].equalsIgnoreCase("portfolio")) {
            sender.sendMessage("§6§lYOUR PORTFOLIO");
            for (var company : market.companies()) {
                int owned = company.sharesOwned(player.getUniqueId());
                if (owned > 0) {
                    sender.sendMessage("§f" + company.ticker() + " §7x" + owned + " §8— §a$"
                            + String.format("%.2f", company.sharePrice() * owned));
                }
            }
            return true;
        }

        sender.sendMessage("§7/trade buy <ticker> <shares> | /trade sell <ticker> <shares> | /trade portfolio");
        return true;
    }
}
