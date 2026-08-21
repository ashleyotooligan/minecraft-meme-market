package dev.mememarket.command;

import dev.mememarket.service.LeaderboardService;
import dev.mememarket.service.MarketService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class MarketCommand implements CommandExecutor {
    private final MarketService market;
    private final LeaderboardService leaderboard = new LeaderboardService();

    public MarketCommand(MarketService market) { this.market = market; }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (args.length >= 2 && args[0].equalsIgnoreCase("quote")) {
            market.company(args[1]).ifPresentOrElse(c -> {
                sender.sendMessage("§6" + c.name() + " §7(" + c.ticker() + ")");
                sender.sendMessage("§fPrice: §a$" + String.format("%.2f", c.sharePrice()));
                sender.sendMessage("§fMarket cap: §a$" + String.format("%.2f", c.marketCap()));
                sender.sendMessage("§fTreasury: §a$" + String.format("%.2f", c.treasury()));
            }, () -> sender.sendMessage("§cUnknown ticker."));
            return true;
        }

        if (args.length >= 1 && args[0].equalsIgnoreCase("top")) {
            sender.sendMessage("§6§lMINECRAFT MEME MARKET — TOP COMPANIES");
            int i = 1;
            for (var c : leaderboard.byMarketCap(market.companies(), 10)) {
                sender.sendMessage("§e#" + i++ + " §f" + c.ticker() + " §7— §a$" + String.format("%.2f", c.marketCap()));
            }
            return true;
        }

        sender.sendMessage("§6§lMINECRAFT MEME MARKET");
        sender.sendMessage("§7Use /market quote <ticker> or /market top");
        return true;
    }
}
