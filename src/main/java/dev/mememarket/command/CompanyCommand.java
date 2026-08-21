package dev.mememarket.command;

import dev.mememarket.service.MarketService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class CompanyCommand implements CommandExecutor {
    private final MarketService market;

    public CompanyCommand(MarketService market) { this.market = market; }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Players only.");
            return true;
        }

        if (args.length >= 3 && args[0].equalsIgnoreCase("create")) {
            try {
                var company = market.createCompany(args[1], args[2], player.getUniqueId(), 10.0, 100);
                sender.sendMessage("§aCreated §f" + company.name() + " §7(" + company.ticker() + ")");
            } catch (IllegalArgumentException ex) {
                sender.sendMessage("§c" + ex.getMessage());
            }
            return true;
        }

        if (args.length >= 2 && args[0].equalsIgnoreCase("info")) {
            market.company(args[1]).ifPresentOrElse(c -> {
                sender.sendMessage("§6" + c.name() + " §7(" + c.ticker() + ")");
                sender.sendMessage("§fShares: §e" + c.totalShares());
                sender.sendMessage("§fPrice: §a$" + String.format("%.2f", c.sharePrice()));
                sender.sendMessage("§fRevenue today: §a$" + String.format("%.2f", c.revenueToday()));
            }, () -> sender.sendMessage("§cUnknown ticker."));
            return true;
        }

        sender.sendMessage("§7/company create <name> <ticker> | /company info <ticker>");
        return true;
    }
}
